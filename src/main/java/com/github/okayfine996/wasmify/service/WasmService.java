package com.github.okayfine996.wasmify.service;

import com.github.okayfine996.wasmify.cmwasm.wasm.WasmClient;
import com.github.okayfine996.wasmify.model.Network;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@State(name = "wasm", storages = @Storage("wasm.xml"))
@Service(Service.Level.APP)
public class WasmService implements PersistentStateComponent<WasmService.WasmState> {
    private WasmState wasmState = new WasmState();

    public WasmService() {

    }

    public String deployWasmContract(String network, String wasmFile, String signerName, String initMsg) {
        var net = wasmState.networkMap.get(network);
        if (net == null) {
            return null;
        }

        Signer signer = wasmState.signerMap.get(signerName);
        if (signer == null) {
            return null;
        }

        WasmClient wasmClient = new WasmClient(net.getRestURL(), net.getChainId(), net.getTxMode());
        return wasmClient.deployWasmContract(signer.value, wasmFile, initMsg);
    }

    public String executeWasmContract(String network, String contractAddress, String signerName, String executeMsg) {
        var net = wasmState.networkMap.get(network);
        if (net == null) {
            return null;
        }

        Signer signer = wasmState.signerMap.get(signerName);
        if (signer == null) {
            return null;
        }
        WasmClient wasmClient = new WasmClient(net.getRestURL(), net.getChainId(), net.getTxMode());
        return wasmClient.executeWasmContract(signer.value, contractAddress, executeMsg);
    }

    public String queryWasmContract(String network, String contractAddress, String queryMsg) {
        var net = wasmState.networkMap.get(network);
        if (net == null) {
            return null;
        }
        WasmClient wasmClient = new WasmClient(net.getRestURL(), net.getChainId(), net.getTxMode());
        return wasmClient.queryWasmContract(contractAddress, queryMsg);
    }

    @Override
    public @Nullable WasmService.WasmState getState() {
        return wasmState;
    }

    @Override
    public void loadState(@NotNull WasmState state) {
        this.wasmState = state;
        if (state.networkMap.size() == 0) {
            addBuiltInNetworks();
        }
    }


    public List<WasmContract> getContractList() {
        return this.wasmState.contractMap.values().stream().collect(Collectors.toList());
    }

    public List<Network> getNetworkList() {
        return this.wasmState.networkMap.values().stream().collect(Collectors.toList());
    }

    public List<Signer> getSignerList() {
        return this.wasmState.signerMap.values().stream().collect(Collectors.toList());
    }


    public void addSigner(WasmService.Signer signer) {
        this.wasmState.signerMap.put(signer.name, signer);
    }

    public void addNetwork(Network network) {
        this.wasmState.networkMap.put(network.getName(), network);
    }


    static class WasmState {
        public Map<String, WasmContract> contractMap;
        public Map<String, Network> networkMap;

        public Map<String, Signer> signerMap;

        public WasmState() {
            this.contractMap = new ConcurrentHashMap<>();
            this.networkMap = new ConcurrentHashMap<>();
            this.signerMap = new ConcurrentHashMap<>();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WasmState wasmState = (WasmState) o;
            return Objects.equals(contractMap, wasmState.contractMap) && Objects.equals(networkMap, wasmState.networkMap) && Objects.equals(signerMap, wasmState.signerMap);
        }

        @Override
        public int hashCode() {
            return Objects.hash(contractMap, networkMap, signerMap);
        }
    }


    private void addBuiltInNetworks() {
        var okbtest = new Network("okbtestnet", "okbchaintest-195", "https://okbtestrpc.okbchain.org", "https://www.oklink.com/cn/okbc-test", "okb", "block");
        this.wasmState.networkMap.put(okbtest.getName(), okbtest);

        var oktctest = new Network("oktctestnet", "exchain-65", "https://exchaintestrpc.okex.org", "https://www.oklink.com/cn/oktc-test", "okt", "block");
        this.wasmState.networkMap.put(oktctest.getName(), oktctest);

        var oktcmainnet = new Network("oktc", "exchain-66", "https://exchainrpc.okex.org", "https://www.oklink.com/cn/oktc", "okt", "denom");
        this.wasmState.networkMap.put(oktcmainnet.getName(), oktcmainnet);
    }

    public static class WasmContract {
        public String contractAddress;
        public String chainName;
        public String signer;

        public WasmContract() {
        }

        public WasmContract(String contractAddress, String chainName, String signer) {
            this.contractAddress = contractAddress;
            this.chainName = chainName;
            this.signer = signer;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WasmContract that = (WasmContract) o;
            return Objects.equals(contractAddress, that.contractAddress) && Objects.equals(chainName, that.chainName) && Objects.equals(signer, that.signer);
        }

        @Override
        public int hashCode() {
            return Objects.hash(contractAddress, chainName, signer);
        }

        @Override
        public String toString() {
            return "WasmContract{" + "contractAddress='" + contractAddress + '\'' + ", chainName='" + chainName + '\'' + ", signer='" + signer + '\'' + '}';
        }
    }

    public static class Signer {
        public String name;
        public String value;

        public Signer() {
        }

        public Signer(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Signer signer = (Signer) o;
            return Objects.equals(name, signer.name) && Objects.equals(value, signer.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, value);
        }

        @Override
        public String toString() {
            return "Signer{" + "name='" + name + '\'' + ", value='" + value + '\'' + '}';
        }
    }
}
