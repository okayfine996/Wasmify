package com.github.okayfine996.wasmify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Network {
    private String name;

    private String chainId;

    private String denom;

    private String restURL;

    private String explorerURL;

    private String txMode;

    public Network() {
    }

    public Network(String name, String chainId, String restURL, String explorerURL, String denom, String txMode) {
        this.name = name;
        this.chainId = chainId;
        this.denom = denom;
        this.restURL = restURL;
        this.explorerURL = explorerURL;
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

    public String getRestURL() {
        return restURL;
    }

    public void setRestURL(String restURL) {
        this.restURL = restURL;
    }

    public String getExplorerURL() {
        return explorerURL;
    }

    public void setExplorerURL(String explorerURL) {
        this.explorerURL = explorerURL;
    }

    public String getTxMode() {
        return txMode;
    }

    public void setTxMode(String txMode) {
        this.txMode = txMode;
    }
}