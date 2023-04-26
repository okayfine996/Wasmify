package com.github.okayfine996.wasmify.cmwasm.wasm;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Fund {

    @JsonProperty("amount")
    @SerializedName("amount")
    private String amount;

    @JsonProperty("denom")
    @SerializedName("denom")
    private String denom;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDenom() {
        return denom;
    }

    public void setDenom(String denom) {
        this.denom = denom;
    }
}
