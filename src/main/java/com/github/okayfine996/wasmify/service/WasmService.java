package com.github.okayfine996.wasmify.service;

import com.github.okayfine996.wasmify.cmwasm.wasm.WasmClient;
import com.github.okayfine996.wasmify.model.Network;
import com.intellij.openapi.components.Service;

import java.util.HashMap;

@Service(Service.Level.APP)
public class WasmService {

    private HashMap<String, Network> networkHashMap = new HashMap<>();

    public WasmService() {
        networkHashMap.put("okb-local", new Network("okb-local", "okbchain-67","okb","http://localhost:8545","block"));
    }

    public String deployWasmContract(String network ,String wasmFile, String account, String initMsg) {
        var net = networkHashMap.get(network);
        if (net == null) {
            return null;
        }

        WasmClient wasmClient = new WasmClient(net.getUrl(),net.getChainId(),net.getTxMode());
        return wasmClient.deployWasmContract(account,wasmFile,initMsg);
    }
}
