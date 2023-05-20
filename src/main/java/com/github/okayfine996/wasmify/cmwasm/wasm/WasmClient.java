package com.github.okayfine996.wasmify.cmwasm.wasm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.okayfine996.wasmify.cmwasm.core.BroadcastTx;
import com.github.okayfine996.wasmify.cmwasm.core.Signer;
import com.github.okayfine996.wasmify.cmwasm.core.StdTx;
import com.github.okayfine996.wasmify.cmwasm.core.TxResponse;
import com.github.okayfine996.wasmify.cmwasm.model.Account;
import com.github.okayfine996.wasmify.cmwasm.utils.Utils;
import com.github.okayfine996.wasmify.cmwasm.utils.crypto.AddressConvertUtil;
import com.github.okayfine996.wasmify.cmwasm.wasm.msg.BaseMsg;
import com.github.okayfine996.wasmify.cmwasm.wasm.msg.ExecuteMsg;
import com.github.okayfine996.wasmify.cmwasm.wasm.msg.InstantiateMsg;
import com.github.okayfine996.wasmify.cmwasm.wasm.msg.StoreCodeMsg;
import okhttp3.*;
import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class WasmClient {

    private String restUrl;

    private String chainId;

    private String txMode;

    private OkHttpClient httpClient;

    public WasmClient(String restUrl, String chainId, String txMode) {
        this.restUrl = restUrl;
        this.txMode = txMode;
        this.chainId = chainId;
        this.httpClient = new OkHttpClient();
    }

    public TxResponse broadcastTx(StdTx stdTx, String nonce) {
        BroadcastTx broadcastTx = new BroadcastTx(this.txMode, stdTx, nonce);
        Response response = null;
        try {
            Request request = new Request.Builder().url(this.restUrl + "/v1/txs").post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), broadcastTx.toJson())).build();

            response = httpClient.newCall(request).execute();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (response != null) {
            try {
                String st = response.body().string();
                return JSON.parseObject(st, TxResponse.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    public int storeCode(String privateKey, String wasmFile, String feeAmount, String denom, String gas) throws IOException {
        byte[] wasmBinary = Utils.compressBytes(wasmFile);
        Signer signer = new Signer(privateKey);
        Account account = this.queryAccount(signer.getAddress());
        if (account == null) {
            throw new RuntimeException("query account failed");
        }
        signer.setAccountNum(account.getAccountNumber() + "");
        signer.setChainId(this.chainId);

        Base64.Encoder encoder = Base64.getEncoder();
        String encodedWasmData = encoder.encodeToString(wasmBinary);
        String jsonStr = "{\"permission\":\"Everybody\"}";
        StoreCodeMsg storeCodeMsg = new StoreCodeMsg(Utils.getSortJson(jsonStr), signer.getAddress(), encodedWasmData);

        StdTx stdTx = signer.buildAndSignStdTx(new BaseMsg<StoreCodeMsg>("wasm/MsgStoreCode", storeCodeMsg), feeAmount,denom, gas, "", account.getSequence() + "");
        TxResponse txResponse = this.broadcastTx(stdTx, account.getSequence() + "");
        if (!txResponse.isSucceed()) {
            throw new RuntimeException(txResponse.toString());
        }

        String log = txResponse.getRawLog();
        int index = log.lastIndexOf(":");
        log = log.substring(index);
        log = log.substring(2, log.indexOf("}") - 1);
        return Integer.valueOf(log);
    }

    public String instantiate(String privateKey, int codeId, String initMsg, String feeAmount, String denom, String gas, List<Fund> funds) throws IOException {
        Signer signer = new Signer(privateKey);
        Account account = this.queryAccount(signer.getAddress());
        if (account == null) {
            throw new RuntimeException("query account failed");
        }
        signer.setAccountNum(account.getAccountNumber() + "");
        signer.setChainId(this.chainId);

        InstantiateMsg instantiateMsg = new InstantiateMsg(AddressConvertUtil.convertFromBech32ToHex(signer.getAddress()), codeId + "", funds, "v1.0.0", Utils.getSortJson(initMsg), signer.getAddress());
        StdTx stdTx = signer.buildAndSignStdTx(new BaseMsg<InstantiateMsg>("wasm/MsgInstantiateContract", instantiateMsg), feeAmount, denom, gas, "", account.getSequence() + "");
        TxResponse txResponse = this.broadcastTx(stdTx, account.getSequence() + "");
        if (!txResponse.isSucceed()) {
            throw new RuntimeException(txResponse.toString());
        }

        String log = txResponse.getRawLog();
        int index = log.indexOf("address");
        return log.substring(index + 18, index + 60);
    }


    public String deployWasmContract(String privateKey, String wasmFile, String initMsg, String feeAmount, String denom, String gas, List<Fund> funds) {
        try {
            int codeId = this.storeCode(privateKey, wasmFile, feeAmount, denom, gas);
            String contractAddress = this.instantiate(privateKey, codeId, initMsg, feeAmount, denom, gas,funds);
            return contractAddress;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String executeWasmContract(String privateKey, String contractAddress, String execMsg,String feeAmount, String denom, String gas,List<Fund> funds) {
        Signer signer = new Signer(privateKey);
        Account account = this.queryAccount(signer.getAddress());
        if (account == null) {
            throw new RuntimeException("query account failed");
        }
        signer.setAccountNum(account.getAccountNumber() + "");
        signer.setChainId(this.chainId);

        ExecuteMsg executeMsg = new ExecuteMsg(contractAddress, funds, Utils.getSortJson(execMsg), signer.getAddress());
        StdTx stdTx = null;
        try {
            stdTx = signer.buildAndSignStdTx(new BaseMsg<ExecuteMsg>("wasm/MsgExecuteContract", executeMsg), feeAmount, denom, gas,"", account.getSequence() + "");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        TxResponse txResponse = this.broadcastTx(stdTx, account.getSequence() + "");
        if (!txResponse.isSucceed()) {
            throw new RuntimeException(txResponse.toString());
        }
        return txResponse.getTxhash();
    }

    public String queryWasmContract(String contractAddress, String queryMsg) {
        String queryKey = Base64.getEncoder().encodeToString(queryMsg.getBytes(Charset.forName("utf8")));
        String url = String.format("%s/v1/wasm/contract/%s/smart/%s?encoding=base64", this.restUrl, contractAddress, queryKey);
        Response response = null;
        try {
            Request request = new Request.Builder().url(url).get().build();
            response = httpClient.newCall(request).execute();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (response != null) {
            try {
                String st = response.body().string();
                return st;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }


    public static void main(String[] args) {
        WasmClient client = new WasmClient("http://127.0.0.1:8545", "okbchain-67", "block");
        String wasmFile = "src/main/java/com/github/okayfine996/wasmify/cmwasm/wasm/hackatom.wasm";
        try {
            int code = client.storeCode("8FF3CA2D9985C3A52B459E2F6E7822B23E1AF845961E22128D5F372FB9AA5F17", wasmFile,"0.03","okb","3000000");
            String initMsg = "{\"verifier\": \"0xbbE4733d85bc2b90682147779DA49caB38C0aA1F\", \"beneficiary\": \"0xbbE4733d85bc2b90682147779DA49caB38C0aA1F\"}";
            String contractAddr = client.instantiate("8FF3CA2D9985C3A52B459E2F6E7822B23E1AF845961E22128D5F372FB9AA5F17", code, initMsg,"0.03","okb","3000000", Collections.emptyList());
            System.out.println(contractAddr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Account queryAccount(String bech32Addr) {
        Request request = new Request.Builder().get().url(restUrl + "/v1/auth/accounts/" + bech32Addr).build();
        Account account = null;
        try {
            Response response = this.httpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                return account;
            }

            String jsonStr = response.body().string();
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            if (!jsonObject.containsKey("value")) {
                return account;
            }

            int accountNum = jsonObject.getJSONObject("value").getIntValue("account_number");
            int sequence = jsonObject.getJSONObject("value").getIntValue("sequence");
            account = new Account(accountNum, sequence);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return account;
    }
}
