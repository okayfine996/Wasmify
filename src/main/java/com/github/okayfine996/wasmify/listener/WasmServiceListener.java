package com.github.okayfine996.wasmify.listener;

import com.intellij.openapi.project.Project;
import com.intellij.util.messages.Topic;

public interface WasmServiceListener {
    @Topic.ProjectLevel
    Topic<WasmServiceListener> TOPIC = new Topic<>("wasm service events", WasmServiceListener.class);

    void deployWasmEvent(Project project, String signer, String contractAddress, String chainName);
}
