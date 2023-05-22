package com.github.okayfine996.wasmify.cmwasm.wasm.msg;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MigrateMsg {
    @JsonProperty("code_id")
    @SerializedName("code_id")
    private String codeId;

    @JsonProperty("contract")
    @SerializedName("contract")
    private String contract;

    @JsonProperty("msg")
    @SerializedName("msg")
    private JSON msg;

    @JsonProperty("sender")
    @SerializedName("sender")
    private String sender;

    public MigrateMsg() {
    }

    public MigrateMsg(String codeId, String contract, JSON msg, String sender) {
        this.codeId = codeId;
        this.contract = contract;
        this.msg = msg;
        this.sender = sender;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public JSON getMsg() {
        return msg;
    }

    public void setMsg(JSON msg) {
        this.msg = msg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "MigrateMsg{" +
                "codeId='" + codeId + '\'' +
                ", contract='" + contract + '\'' +
                ", msg=" + msg +
                ", sender='" + sender + '\'' +
                '}';
    }
}
