package com.github.okayfine996.wasmify.listener;

import com.github.okayfine996.wasmify.notify.Notifier;
import com.github.okayfine996.wasmify.service.WasmContract;
import com.github.okayfine996.wasmify.service.WasmService;
import com.github.okayfine996.wasmify.toolWindow.WasmToolWindowFactory;
import com.github.okayfine996.wasmify.utils.ToolWindowUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;

public class MyWasmServiceListener implements WasmServiceListener {
    private WasmService wasmService = ApplicationManager.getApplication().getService(WasmService.class);

    @Override
    public void deployWasmEvent(Project project, String signer, String contractAddress, String chainName) {
        WasmService wasmService = project.getService(WasmService.class);
        wasmService.addContract(new WasmContract(contractAddress, chainName, signer));
        ToolWindowUtil.getContractPanel(project).updatePanel();
        Notifier.notifyInfo(null, "Deploy Success " + contractAddress);
    }
}
