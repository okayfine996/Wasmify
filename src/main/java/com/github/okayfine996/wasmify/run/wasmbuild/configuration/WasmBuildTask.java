package com.github.okayfine996.wasmify.run.wasmbuild.configuration;


import com.intellij.execution.BeforeRunTask;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NotNull;

public class WasmBuildTask extends BeforeRunTask<WasmBuildTask> {

    protected WasmBuildTask(@NotNull Key providerId) {
        super(providerId);
        setEnabled(true);
    }

}
