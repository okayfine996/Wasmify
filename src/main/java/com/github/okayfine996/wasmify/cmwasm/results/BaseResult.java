package com.github.okayfine996.wasmify.cmwasm.results;

import com.github.okayfine996.wasmify.cmwasm.core.TxResponse;


public abstract class BaseResult {
    private TxResponse txResponse;

    public BaseResult(TxResponse txResponse) {
        this.txResponse = txResponse;
    }

    public boolean isSuccess() {
        return txResponse.getCode() == 0;
    }

    public String txHash() {
        return txResponse.getTxhash();
    }

    public TxResponse getTxResponse() {
        return txResponse;
    }
}
