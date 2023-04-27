package com.github.okayfine996.wasmify.run.deploy.configuration;

import com.intellij.execution.lineMarker.ExecutorAction;
import com.intellij.execution.lineMarker.RunLineMarkerContributor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rust.lang.core.psi.impl.RsFunctionImpl;

public class WasmDeployRunLineMarker extends RunLineMarkerContributor {
    @Override
    public @Nullable Info getInfo(@NotNull PsiElement element) {

        Project project = element.getProject();



        if (isInstantiate(element)) {
            return new RunLineMarkerContributor.Info(ExecutorAction.getActions()[0]);
        }


        return null;
    }


    private boolean isInstantiate(PsiElement element) {
        if (element instanceof RsFunctionImpl) {
            RsFunctionImpl function = (RsFunctionImpl) element;

            System.out.println(function.toString());

            String funName = function.getName();
            String marcos = function.getFirstChild().getText().trim();
            if (funName.equals("instantiate") && marcos.equals("#[msg(instantiate)]")) {
                return true;
            }
        }

        return false;
    }
}
