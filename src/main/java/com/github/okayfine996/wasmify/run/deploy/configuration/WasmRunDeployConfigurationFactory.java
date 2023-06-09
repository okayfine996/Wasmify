package com.github.okayfine996.wasmify.run.deploy.configuration;

import com.intellij.execution.BeforeRunTask;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.components.BaseState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WasmRunDeployConfigurationFactory extends ConfigurationFactory {

    protected WasmRunDeployConfigurationFactory(@NotNull ConfigurationType type) {
        super(type);
    }

    @Override
    public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        WasmRunDeployConfiguration wasmRunDeployConfiguration = new WasmRunDeployConfiguration(project,this,"DeployWasmContract");
        return wasmRunDeployConfiguration;
    }

    @Override
    public @NotNull @NonNls String getId() {
        return WasmRunDeployConfigurationType.ID;
    }

    @Override
    public @Nullable Class<? extends BaseState> getOptionsClass() {
        return WasmRunDeployConfigurationOption.class;
    }

}
