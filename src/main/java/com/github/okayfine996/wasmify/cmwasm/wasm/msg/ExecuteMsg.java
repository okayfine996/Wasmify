package com.github.okayfine996.wasmify.cmwasm.wasm.msg;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.okayfine996.wasmify.cmwasm.wasm.Fund;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class ExecuteMsg {


    @JsonProperty("contract")
    @SerializedName("contract")
    private String contract;


    @JsonProperty("funds")
    @SerializedName("funds")
    private List<Fund> fund;


    @JsonProperty("msg")
    @SerializedName("msg")
    private JSON msg;


    @JsonProperty("sender")
    @SerializedName("sender")
    private String sender;


    public ExecuteMsg() {
    }

    public ExecuteMsg(String contract, List<Fund> fund, JSON msg, String sender) {
        this.contract = contract;
        this.fund = fund;
        this.msg = msg;
        this.sender = sender;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public List<Fund> getFund() {
        return fund;
    }

    public void setFund(List<Fund> fund) {
        this.fund = fund;
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
}
