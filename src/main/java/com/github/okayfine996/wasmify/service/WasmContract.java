package com.github.okayfine996.wasmify.service;

import java.util.Objects;

public class WasmContract {
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