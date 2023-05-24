package com.github.okayfine996.wasmify.tasks;

import com.github.okayfine996.wasmify.cmwasm.wasm.Fund;
import com.github.okayfine996.wasmify.listener.WasmServiceListener;
import com.github.okayfine996.wasmify.service.WasmService;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;

import java.util.List;

public class DeployWasmContractTask extends Task.Backgroundable {
    private static final String TASK_NAME = "deploy wasm";
    private Project project;
    private String network;
    private String wasmFile;
    private String initMsg;
    private String signer;

    private String fee;

    private String gas;

    private List<Fund> fundList;

    public DeployWasmContractTask(@Nullable Project project, String network, String wasmFile, String initMsg, String signer, String fee, String gas, List<Fund> funds) {
        super(project, TASK_NAME);
        this.project = project;
        this.network = network;
        this.wasmFile = wasmFile;
        this.initMsg = initMsg;
        this.signer = signer;
        this.fee = fee;
        this.gas = gas;
        this.fundList = funds;
    }


    @Override
    public void run(@NotNull ProgressIndicator indicator) {
        WasmService wasmService = project.getService(WasmService.class);
        var contractAddress = wasmService.deployWasmContract(network, wasmFile, signer, initMsg, fee, gas, fundList);

        if (contractAddress != null) {
            MessageBus messageBus = this.project.getMessageBus();
            WasmServiceListener publisher = messageBus.syncPublisher(WasmServiceListener.TOPIC);
            publisher.deployWasmEvent(project,signer, contractAddress, network);
        }
    }
}