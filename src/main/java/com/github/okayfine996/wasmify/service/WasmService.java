package com.github.okayfine996.wasmify.service;

import com.github.okayfine996.wasmify.cmwasm.wasm.WasmClient;
import com.github.okayfine996.wasmify.model.Network;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@State(name = "wasm", storages = @Storage("wasm.xml"))
@Service(Service.Level.APP)
public class WasmService implements PersistentStateComponent<WasmService.WasmState> {

    private HashMap<String, Network> networkHashMap = new HashMap<>();
    private WasmState wasmState = new WasmState();

    public WasmService() {

    }

    public String deployWasmContract(String network, String wasmFile, String signerName, String initMsg) {
        var net = networkHashMap.get(network);
        if (net == null) {
            return null;
        }

        Signer signer = null;
        for (int i = 0; i < wasmState.signerList.size(); i++) {
            if (wasmState.signerList.get(i).equals(signerName)) {
                signer = wasmState.signerList.get(i);
            }
        }
        if (signer == null) {
            return null;
        }

        WasmClient wasmClient = new WasmClient(net.getUrl(), net.getChainId(), net.getTxMode());
        return wasmClient.deployWasmContract(signer.value, wasmFile, initMsg);
    }

    public String executeWasmContract(String network, String contractAddress, String account, String executeMsg) {
        var net = networkHashMap.get(network);
        if (net == null) {
            return null;
        }
        WasmClient wasmClient = new WasmClient(net.getUrl(), net.getChainId(), net.getTxMode());
        return wasmClient.executeWasmContract(account, contractAddress, executeMsg);
    }

    public String queryWasmContract(String network, String contractAddress, String queryMsg) {
        var net = networkHashMap.get(network);
        if (net == null) {
            return null;
        }
        WasmClient wasmClient = new WasmClient(net.getUrl(), net.getChainId(), net.getTxMode());
        return wasmClient.queryWasmContract(contractAddress, queryMsg);
    }

    @Override
    public @Nullable WasmService.WasmState getState() {
        if (wasmState.networkList.size() == 0) {
            wasmState.getNetworkList().add(new Network("okb-local", "okbchain-67", "okb", "http://localhost:8545", "block"));
        }
        return wasmState;
    }

    @Override
    public void loadState(@NotNull WasmState state) {
        this.wasmState = state;
        state.networkList.forEach(e -> networkHashMap.put(e.getChainId(), e));
    }


    public List<WasmContract> getContractList() {
        return this.wasmState.contractList;
    }

    public List<Network> getNetworkList() {
        return this.wasmState.networkList;
    }

    public List<Signer> getSignerList() {
        return this.wasmState.signerList;
    }


    public void addSigner(WasmService.Signer signer) {
        this.wasmState.signerList.add(signer);
    }


    static class WasmState {
        public List<WasmContract> contractList;
        public List<Network> networkList;

        public List<Signer> signerList;

        public WasmState() {
            this.contractList = new ArrayList<>();
            this.networkList = new ArrayList<>();
            this.signerList = new ArrayList<>();
        }

        public WasmState(List<WasmContract> contractList, List<Network> networkList) {
            this.contractList = contractList;
            this.networkList = networkList;
        }

        public List<WasmContract> getContractList() {
            return contractList;
        }

        public void setContractList(List<WasmContract> contractList) {
            this.contractList = contractList;
        }

        public List<Network> getNetworkList() {
            return networkList;
        }

        public void setNetworkList(List<Network> networkList) {
            this.networkList = networkList;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WasmState wasmState = (WasmState) o;
            return Objects.equals(contractList, wasmState.contractList) && Objects.equals(networkList, wasmState.networkList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(contractList, networkList);
        }

        @Override
        public String toString() {
            return "WasmState{" + "contractList=" + contractList + ", networkList=" + networkList + '}';
        }
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
            return "Signer{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}
