package com.github.okayfine996.wasmify.cmwasm.wasm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.okayfine996.wasmify.cmwasm.core.BroadcastTx;
import com.github.okayfine996.wasmify.cmwasm.core.Signer;
import com.github.okayfine996.wasmify.cmwasm.core.StdTx;
import com.github.okayfine996.wasmify.cmwasm.utils.Utils;
import com.github.okayfine996.wasmify.cmwasm.wasm.msg.BaseMsg;
import com.github.okayfine996.wasmify.cmwasm.wasm.msg.StoreCodeMsg;
import okhttp3.*;

import java.io.IOException;
import java.util.Base64;

public class WasmClient {

    private String restUrl;

    private String chainId = "exchain-67";

    private String txMode;

    private OkHttpClient httpClient;

    public WasmClient(String restUrl, String txMode) {
        this.restUrl = restUrl;
        this.txMode = txMode;
        this.httpClient = new OkHttpClient();
    }

    public Object broadcastTx(StdTx stdTx) {
        BroadcastTx broadcastTx = new BroadcastTx(this.txMode, stdTx);
        Response response = null;
        try {
            Request request = new Request.Builder()
                    .url(this.restUrl)
                    .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), broadcastTx.toJson()))
                    .build();
            System.out.println(broadcastTx.toJson());
            response = httpClient.newCall(request).execute();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (response != null) {
            try {
                String st = response.body().string();
                System.out.println(st);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    public int storeCode(String privateKey, String accountNumber, String nonce, String wasmFile) throws IOException {
        byte[] wasmBinary = Utils.compressBytes(wasmFile);
        Signer signer = new Signer(privateKey);

        Base64.Encoder encoder = Base64.getEncoder();
        String encodedWasmData = encoder.encodeToString(wasmBinary);
        String jsonStr = "{\"permission\":\"Everybody\"}";
        StoreCodeMsg storeCodeMsg = new StoreCodeMsg(Utils.getSortJson(jsonStr), signer.getAddress(), encodedWasmData);


        signer.setAccountNum(accountNumber);
        signer.setChainId(this.chainId);
        StdTx stdTx = signer.buildAndSignStdTx(new BaseMsg<StoreCodeMsg>("wasm/MsgStoreCode", storeCodeMsg), "0.03", "30000000", "", nonce);
        this.broadcastTx(stdTx);

        return 0;
    }


    public static void main(String[] args) {
        WasmClient client = new WasmClient("http://127.0.0.1:8545/exchain/v1/txs","block");
        String wasmFile = "/Users/finefine/workspace/java/Wasmify/src/main/java/com/github/okayfine996/wasmify/cmwasm/wasm/cw20_base.wasm";
        try {
            client.storeCode("8FF3CA2D9985C3A52B459E2F6E7822B23E1AF845961E22128D5F372FB9AA5F17","0","1", wasmFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
