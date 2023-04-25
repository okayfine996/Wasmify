package com.github.okayfine996.wasmify.runconfiguration;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.icons.AllIcons;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RunConfigurationType implements ConfigurationType {
    static final String ID = "WasmRunConfiguration";
    @Override
    public @NotNull @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return "Wasm";
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Sentence) String getConfigurationTypeDescription() {
        return "Wasm contract";
    }

    @Override
    public Icon getIcon() {
        return AllIcons.General.Information;
    }

    @Override
    public @NotNull @NonNls String getId() {
        return ID;
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{new RunConfigurationFactory(this)};
    }
}
