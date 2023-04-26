package com.github.okayfine996.wasmify.run.deploy.configuration;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.LazyRunConfigurationProducer;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;

import java.util.TimerTask;

public class WasmDeployRuntimeConfigurationProducer extends LazyRunConfigurationProducer<WasmRunDeployConfiguration> {

    @Override
    protected boolean setupConfigurationFromContext(@NotNull WasmRunDeployConfiguration configuration, @NotNull ConfigurationContext context, @NotNull Ref<PsiElement> sourceElement) {
        configuration.setScriptName("hhhhhhh");
        System.out.println("hhhhhhh");
        System.out.println(context);
        return true;
    }

    @Override
    public boolean isConfigurationFromContext(@NotNull WasmRunDeployConfiguration configuration, @NotNull ConfigurationContext context) {
        return true;
    }

    @NotNull
    @Override
    public ConfigurationFactory getConfigurationFactory() {
        return new WasmRunDeployConfigurationFactory(new WasmRunDeployConfigurationType());
    }
}
