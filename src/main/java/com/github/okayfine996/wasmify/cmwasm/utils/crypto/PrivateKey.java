package com.github.okayfine996.wasmify.cmwasm.utils.crypto;

import org.bouncycastle.util.encoders.Hex;

public class PrivateKey {

    protected String pubKeyString;
    protected String address;
    protected String priKeyString;


    public PrivateKey(String priKeyString) {
        this.pubKeyString = Hex.toHexString(Crypto.generatePubKeyFromPriv(priKeyString));
        this.address = Crypto.generateAddressFromPriv(priKeyString);
        this.priKeyString = priKeyString;
    }

    public String getAddress() {
        return address;
    }

    public String getPubKey() {
        return pubKeyString;
    }

    public String getPriKey() {
        return priKeyString;
    }
}
