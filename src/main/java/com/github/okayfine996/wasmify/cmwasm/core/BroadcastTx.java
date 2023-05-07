package com.github.okayfine996.wasmify.cmwasm.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class BroadcastTx {
    private String mode;
    private StdTx tx;

    private String nonce;


    public BroadcastTx() {
    }

    public BroadcastTx(String mode, StdTx tx, String nonce) {
        this.mode = mode;
        this.tx = tx;
        this.nonce = nonce;
    }

    public StdTx getTx() {
        return tx;
    }

    public void setTx(StdTx value) {
        this.tx = value;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

}
