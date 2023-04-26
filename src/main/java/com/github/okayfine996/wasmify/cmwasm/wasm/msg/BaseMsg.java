package com.github.okayfine996.wasmify.cmwasm.wasm.msg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class BaseMsg<T> {
    @JsonProperty("type")
    @SerializedName("type")
    private String msgType;

    @JsonProperty("value")
    @SerializedName("value")
    private T value;

    public BaseMsg(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgType() {
        return msgType;
    }

    public BaseMsg(String msgType, T value) {
        this.msgType = msgType;
        this.value = value;
    }
}
