package com.github.okayfine996.wasmify.cmwasm.core;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.okayfine996.wasmify.cmwasm.utils.Utils;
import com.github.okayfine996.wasmify.cmwasm.utils.crypto.AddressConvertUtil;
import com.github.okayfine996.wasmify.cmwasm.utils.crypto.Crypto;
import com.github.okayfine996.wasmify.cmwasm.utils.crypto.PrivateKey;
import com.github.okayfine996.wasmify.cmwasm.wasm.msg.BaseMsg;
import groovy.util.logging.Slf4j;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
public class Signer {

    private String chainId;
    protected String accountNum;
    protected PrivateKey privateKey;


    public Signer(String privateKey) {
        String pattern = "[a-fA-F0-9]{64}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(privateKey);
        if (!m.find()) {
            privateKey = Crypto.privateKeyFromMnemonic(privateKey);
        }
        this.privateKey = new PrivateKey(privateKey);
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    private StdTx buildUnsignedTx(BaseMsg msg, String feeAmount, String gas, String memo, String nonce, String denom) {
        Fee fee = new Fee();
        List<Token> amountList = new ArrayList<>();
        Token token = new Token(Utils.NewDecString(feeAmount), denom);
        amountList.add(token);
        fee.setAmount(amountList);
        fee.setGas(gas);

        BaseMsg[] msgs = new BaseMsg[]{msg};
        StdTx txValue = new StdTx();
        txValue.setMsgs(msgs);
        txValue.setFee(fee);
        txValue.setMemo(memo);

        return txValue;
    }


    public StdTx buildAndSignStdTx(BaseMsg msg, String feeAmount, String gas, String memo, String nonce, String denom) throws JsonProcessingException {
        StdTx stdTx = buildUnsignedTx(msg, feeAmount, gas, memo, nonce, denom);
        Signature signature = signTx(stdTx, nonce);
        stdTx.setSignatures(Arrays.asList(signature));
        return stdTx;
    }

    private Signature signTx(StdTx stdTx, String nonce) throws JsonProcessingException {
        Data2Sign data = new Data2Sign(accountNum, chainId, stdTx.getFee(), stdTx.getMemo(), stdTx.getMsgs(), nonce);
        String unsignedTxJson = new ObjectMapper().writeValueAsString(data);

        Signature signature = null;
        try {
            byte[] byteSignData = unsignedTxJson.getBytes();
            BigInteger privKey = new BigInteger(this.privateKey.getPriKey(), 16);
            Sign.SignatureData sig = Sign.signMessage(byteSignData, ECKeyPair.create(privKey));
            String sigResult = toBase64(sig);

            signature = new Signature();
            Pubkey pubkey = new Pubkey();
            pubkey.setValue(Strings.fromByteArray(Base64.encode(Hex.decode(Crypto.generatePubKeyHexFromPriv(this.privateKey.getPriKey())))));
            signature.setPubkey(pubkey);
            signature.setSignature(sigResult);
        } catch (Exception exception) {

        }
        return signature;
    }


    public static String toBase64(Sign.SignatureData sig) {
        byte[] sigData = new byte[64];  // 32 bytes for R + 32 bytes for S
        System.arraycopy(sig.getR(), 0, sigData, 0, 32);
        System.arraycopy(sig.getS(), 0, sigData, 32, 32);
        return new String(Base64.encode(sigData), Charset.forName("UTF-8"));
    }


    public String getAddress() {
        return this.privateKey.getAddress();
    }

    public String getHexAddress() {
        return AddressConvertUtil.convertAddressFromBech32ToHex(this.privateKey.getAddress());
    }
}
