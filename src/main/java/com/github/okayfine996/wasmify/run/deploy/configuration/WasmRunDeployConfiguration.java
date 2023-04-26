package com.github.okayfine996.wasmify.run.deploy.configuration;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessHandlerFactory;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsActions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WasmRunDeployConfiguration extends LocatableConfigurationBase<WasmRunDeployConfigurationOption> {
    protected WasmRunDeployConfiguration(@NotNull Project project, @Nullable ConfigurationFactory factory, @Nullable String name) {
        super(project, factory, name);
    }

    @Override
    public @NotNull SettingsEditor<? extends com.intellij.execution.configurations.RunConfiguration> getConfigurationEditor() {
        return new WasmRunDeploySettingsEditor();
    }

    @Override
    public @Nullable RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) throws ExecutionException {
        return new CommandLineState(environment) {
            @NotNull
            @Override
            protected ProcessHandler startProcess() throws ExecutionException {
                GeneralCommandLine commandLine = new GeneralCommandLine(getOptions().getScriptName());
                OSProcessHandler processHandler = ProcessHandlerFactory.getInstance().createColoredProcessHandler(commandLine);
                ProcessTerminatedListener.attach(processHandler);
                return processHandler;
            }

            @Override
            public @NotNull ExecutionResult execute(@NotNull Executor executor, @NotNull ProgramRunner<?> runner) throws ExecutionException {
                return super.execute(executor, runner);
            }
        };
    }





    @Override
    protected @NotNull WasmRunDeployConfigurationOption getOptions() {
        return (WasmRunDeployConfigurationOption)super.getOptions();
    }

    public String getScriptName() {
        return getOptions().getScriptName();
    }

    public void setScriptName(String scriptName) {
        getOptions().setScriptName(scriptName);
    }
}
