package com.github.okayfine996.wasmify.cmwasm.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Pubkey {

    @JsonProperty("type")
    @SerializedName("type")
    private String type;

    @JsonProperty("value")
    @SerializedName("value")
    private String value;

    public Pubkey() {
        this.type = "ethermint/PubKeyEthSecp256k1";
    }

    public Pubkey(String value) {
        this();
        this.value = value;
    }


    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("type", type)
                .append("value", value)
                .toString();
    }
}
