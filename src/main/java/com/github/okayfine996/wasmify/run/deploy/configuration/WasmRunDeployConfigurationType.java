package com.github.okayfine996.wasmify.run.deploy.configuration;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.icons.AllIcons;
import icons.SdkIcons;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class WasmRunDeployConfigurationType implements ConfigurationType {
    static final String ID = "WasmRunDeployConfiguration";
    @Override
    public @NotNull @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return "CosmWasm";
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Sentence) String getConfigurationTypeDescription() {
        return "Build Wasm";
    }

    @Override
    public Icon getIcon() {
        return SdkIcons.Sdk_default_icon;
    }

    @Override
    public @NotNull @NonNls String getId() {
        return ID;
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{new WasmRunDeployConfigurationFactory(this)};
    }
}
