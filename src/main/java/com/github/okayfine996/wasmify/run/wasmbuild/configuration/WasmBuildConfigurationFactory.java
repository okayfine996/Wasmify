package com.github.okayfine996.wasmify.run.wasmbuild.configuration;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class WasmBuildConfigurationFactory extends ConfigurationFactory {

    protected WasmBuildConfigurationFactory(@NotNull ConfigurationType type) {
        super(type);
    }

    @Override
    public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        WasmBuildConfiguration wasmRunDeployConfiguration = new WasmBuildConfiguration(project,this,"BuildWasmContract");
        return wasmRunDeployConfiguration;
    }
}
