package com.github.okayfine996.wasmify.cmwasm.wasm.msg;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.okayfine996.wasmify.cmwasm.wasm.Fund;
import com.google.gson.annotations.SerializedName;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class InstantiateMsg {
    @JsonProperty("admin")
    @SerializedName("admin")
    private String admin;

    @JsonProperty("code_id")
    @SerializedName("code_id")
    private String codeId;

    @JsonProperty("funds")
    @SerializedName("funds")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Fund> fund;

    @JsonProperty("label")
    @SerializedName("label")
    private String label;

    @JsonProperty("msg")
    @SerializedName("msg")
    private JSON msg;

    @JsonProperty("sender")
    @SerializedName("sender")
    private String sender;


    public InstantiateMsg(String admin, String codeId, List<Fund> fund, String label, JSON msg, String sender) {
        this.admin = admin;
        this.codeId = codeId;
        this.fund = fund;
        this.label = label;
        this.msg = msg;
        this.sender = sender;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public List<Fund> getFund() {
        return fund;
    }

    public void setFund(List<Fund> fund) {
        this.fund = fund;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
