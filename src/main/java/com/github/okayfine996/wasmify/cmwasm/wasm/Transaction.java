package com.github.okayfine996.wasmify.cmwasm.wasm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Transaction<T> {


    private String mode = "block";

    private String type = "cosmos-sdk/StdTx";

    private T tx;
}
