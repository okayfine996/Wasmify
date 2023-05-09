package com.github.okayfine996.wasmify.listener;

import com.intellij.util.messages.Topic;

public interface WasmServiceListener {
    @Topic.ProjectLevel
    Topic<WasmServiceListener> TOPIC = new Topic<>("wasm service events", WasmServiceListener.class);

    void deployWasmEvent(String signer,String contractAddress, String chainName);
}
