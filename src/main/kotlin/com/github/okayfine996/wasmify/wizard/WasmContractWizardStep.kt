package com.github.okayfine996.wasmify.wizard

import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import javax.swing.JComponent
import javax.swing.JLabel

import kotlin.collections.ArrayList


class WasmContractWizardStep : ModuleBuilder() {
    override fun getModuleType(): ModuleType<*> {
        return ModuleType.EMPTY
    }


    override fun createWizardSteps(wizardContext: WizardContext, modulesProvider: ModulesProvider): Array<ModuleWizardStep> {

       return arrayOf(MyWizard())
    }

    class MyWizard : ModuleWizardStep() {
        override fun getComponent(): JComponent {
            return JLabel("hello wasm contract")
        }

        override fun updateDataModel() {

        }

    }

}