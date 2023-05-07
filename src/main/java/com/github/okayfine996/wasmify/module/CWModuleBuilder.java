package com.github.okayfine996.wasmify.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.ModuleType;
import org.jetbrains.annotations.Nullable;

public class CWModuleBuilder extends ModuleBuilder {
    @Override
    public ModuleType<?> getModuleType() {
        return CWModuleType.getInstance();
    }

    @Override
    public @Nullable ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        CWModuleWizardStep  cwModuleWizardStep = new CWModuleWizardStep();
        return cwModuleWizardStep;
    }


}
