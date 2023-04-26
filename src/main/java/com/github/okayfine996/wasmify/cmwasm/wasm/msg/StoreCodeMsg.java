package com.github.okayfine996.wasmify.cmwasm.wasm.msg;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.alibaba.fastjson.JSON;
import com.google.gson.annotations.SerializedName;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class StoreCodeMsg {

    @JsonProperty("instantiate_permission")
    @SerializedName("instantiate_permission")
    private JSON instantiatePermission;

    @JsonProperty("sender")
    @SerializedName("sender")
    private String sender;

    @JsonProperty("wasm_byte_code")
    @SerializedName("wasm_byte_code")
    private String wasmByteCode;


    public StoreCodeMsg() {
    }

    public StoreCodeMsg(JSON instantiatePermission, String sender, String wasmByteCode) {
        this();
        this.instantiatePermission = instantiatePermission;
        this.sender = sender;
        this.wasmByteCode = wasmByteCode;
    }

    public JSON getInstantiatePermission() {
        return instantiatePermission;
    }

    public void setInstantiatePermission(JSON instantiatePermission) {
        this.instantiatePermission = instantiatePermission;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getWasmByteCode() {
        return wasmByteCode;
    }

    public void setWasmByteCode(String wasmByteCode) {
        this.wasmByteCode = wasmByteCode;
    }
}
