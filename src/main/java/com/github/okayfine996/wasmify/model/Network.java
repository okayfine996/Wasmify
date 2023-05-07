package com.github.okayfine996.wasmify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Network {
    private String name;

    private String chainId;

    private String denom;

    private String url;

    private String txMode;

    public Network() {
    }

    public Network(String name, String chainId, String denom, String url, String txMode) {
        this.name = name;
        this.chainId = chainId;
        this.denom = denom;
        this.url = url;
        this.txMode = txMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public String getDenom() {
        return denom;
    }

    public void setDenom(String denom) {
        this.denom = denom;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTxMode() {
        return txMode;
    }

    public void setTxMode(String txMode) {
        this.txMode = txMode;
    }
}