package com.github.okayfine996.wasmify.listener;

import com.github.okayfine996.wasmify.cmwasm.results.MigrateResult;
import com.github.okayfine996.wasmify.cmwasm.wasm.Fund;
import com.github.okayfine996.wasmify.notify.Notifier;
import com.github.okayfine996.wasmify.service.WasmService;
import com.github.okayfine996.wasmify.toolWindow.WasmToolWindowFactory;
import com.github.okayfine996.wasmify.ui.contract.WasmContract;
import com.intellij.openapi.application.ApplicationManager;

import java.util.List;

public class MyWasmServiceListener implements WasmServiceListener {
    private WasmService wasmService = ApplicationManager.getApplication().getService(WasmService.class);

    @Override
    public void deployWasmEvent(String signer, String contractAddress, String chainName) {
        WasmContract wasmContract = new WasmContract(contractAddress, chainName, signer);
        wasmService.addContract(new WasmService.WasmContract(contractAddress, chainName, signer));
        WasmToolWindowFactory.Companion.getContractPanel().updatePanel();
//        wasmContract.setWasmContractActionListener(new WasmContract.WasmContractActionListener() {
//
//            @Override
//            public String execute(String signer, String contractAddress, String executeMsg, String chain, String fee, String gas, List<Fund> funds) {
//                return wasmService.executeWasmContract(chain, contractAddress, signer, executeMsg, fee, gas, funds);
//            }
//
//            @Override
//            public String query(String contractAddress, String queryMsg, String chain) {
//                return wasmService.queryWasmContract(chain, contractAddress, queryMsg);
//            }
//
//            @Override
//            public MigrateResult migrate(String signer, String contractAddress, String migrateMsg, String chain, String wasmFile, String fee, String gas, int fund) {
//                return wasmService.updateWasmContract(chain, contractAddress, wasmFile, signer, migrateMsg, fee, gas, fund);
//            }
//        });

        Notifier.notifyInfo(null, "Deploy Success " + contractAddress);
    }
}
