package com.github.okayfine996.wasmify.runconfiguration;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.components.BaseState;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RunConfigurationFactory extends ConfigurationFactory {

    protected RunConfigurationFactory(@NotNull ConfigurationType type) {
        super(type);
    }

    @Override
    public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new com.github.okayfine996.wasmify.runconfiguration.RunConfiguration(project,this,"demo");
    }

    @Override
    public @NotNull @NonNls String getId() {
        return RunConfigurationType.ID;
    }

    @Override
    public @Nullable Class<? extends BaseState> getOptionsClass() {
        return RunConfigurationOption.class;
    }
}
