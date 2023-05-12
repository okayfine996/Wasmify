package com.github.okayfine996.wasmify.run.wasmbuild.configuration;

import com.intellij.execution.BeforeRunTaskProvider;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.impl.ExecutionManagerImpl;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.util.Key;
import com.intellij.task.ProjectTask;
import com.intellij.task.ProjectTaskManager;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class WasmBuildTaskProvider extends BeforeRunTaskProvider<WasmBuildTask> {

    private static Key<WasmBuildTask>  ID = Key.create("WASM_PACK_BUILD_TASK_PROVIDER");
    @Override
    public Key<WasmBuildTask> getId() {
        return ID;
    }

    @Override
    public @Nullable Icon getIcon() {
        return AllIcons.Actions.Compile;
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getName() {
        return "Build";
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Sentence) String getDescription(WasmBuildTask task) {
        return "Build Wasm";
    }


    @Override
    public @Nullable WasmBuildTask createTask(@NotNull RunConfiguration runConfiguration) {
            return new WasmBuildTask(ID);
    }

    @Override
    public boolean executeTask(@NotNull DataContext context, @NotNull RunConfiguration configuration, @NotNull ExecutionEnvironment environment, @NotNull WasmBuildTask task) {
        return doExecuteTask(environment);
    }


    protected boolean doExecuteTask(ExecutionEnvironment environment) {

        CompletableFuture<Boolean> result = new CompletableFuture<>();
        Object sessionId = ExecutionManagerImpl.EXECUTION_SESSION_ID_KEY.get(environment);
        ProjectTaskManager projectTaskManager = ProjectTaskManager.getInstance(environment.getProject());
        WasmBuildConfigurationFactory factory = new WasmBuildConfigurationFactory(new WasmBuildConfigurationType());

        ProjectTask projectTask = new ProjectTask() {
            @Override
            public @NotNull @Nls String getPresentableName() {
                return "Build Wasm";
            }
        };

//        ProjectTaskManager.getInstance(environment.getProject()).run(new ProjectTaskContext(sessionId, factory.createTemplateConfiguration(environment.getProject())),projectTask).onProcessed(e -> {
//            result.complete(!e.hasErrors() && !e.isAborted());
//        });
        ProjectTaskManager.getInstance(environment.getProject()).buildAllModules().onProcessed(e->{
            result.complete(!e.hasErrors() && !e.isAborted());
        });
        try {
            return result.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
