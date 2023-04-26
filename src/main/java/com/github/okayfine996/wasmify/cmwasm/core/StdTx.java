package com.github.okayfine996.wasmify.cmwasm.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.okayfine996.wasmify.cmwasm.wasm.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StdTx {

    @JsonProperty("msg")
    @SerializedName("msg")
    private BaseMsg[] msgs;

    @JsonProperty("fee")
    @SerializedName("fee")
    private Fee fee;

    @JsonProperty("signatures")
    @SerializedName("signatures")
    private List<Signature> signatures;

    @JsonProperty("memo")
    @SerializedName("memo")
    private String memo;

    public BaseMsg[] getMsgs() {
        return msgs;
    }

    public void setMsgs(BaseMsg[] msgs) {
        this.msgs = msgs;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public Fee getFee() {
        return fee;
    }

    public List<Signature> getSignatures() {
        return signatures;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setSignatures(List<Signature> signatures) {
        this.signatures = signatures;
    }
}
