package com.github.okayfine996.wasmify.cmwasm.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class BroadcastTx {
    private String mode;
    private String type = "cosmos-sdk/StdTx";
    private StdTx tx;


    public BroadcastTx() {
    }

    public BroadcastTx(String mode, StdTx tx) {
        this.mode = mode;
        this.tx = tx;
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


//    public String getType() {
//        return type;
//    }

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

}
