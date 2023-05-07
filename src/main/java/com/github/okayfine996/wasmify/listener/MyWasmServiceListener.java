package com.github.okayfine996.wasmify.listener;

import com.intellij.openapi.wm.ToolWindowManager;

public class MyWasmServiceListener implements WasmServiceListener{
    @Override
    public void deployWasmEvent(String contractAddress) {

        System.out.println(contractAddress);
    }

    @Override
    public void update() {

    }
}
