package com.github.okayfine996.wasmify.framework;

import com.intellij.framework.FrameworkTypeEx;
import com.intellij.framework.addSupport.FrameworkSupportInModuleConfigurable;
import com.intellij.framework.addSupport.FrameworkSupportInModuleProvider;
import com.intellij.ide.util.frameworkSupport.FrameworkSupportModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.roots.ModifiableModelsProvider;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class WasmFramework extends FrameworkTypeEx {
    public static final String FRAMEWORK_ID = "com.github.okayfine996.wasmify.framework.WasmFramework";
    protected WasmFramework() {
        super(FRAMEWORK_ID);
    }

    @Override
    public @NotNull FrameworkSupportInModuleProvider createProvider() {
        return new FrameworkSupportInModuleProvider() {
            @NotNull
            @Override
            public FrameworkTypeEx getFrameworkType() {
                return WasmFramework.this;
            }

            @NotNull
            @Override
            public FrameworkSupportInModuleConfigurable createConfigurable(@NotNull FrameworkSupportModel model) {
                return new FrameworkSupportInModuleConfigurable() {

                    @Override
                    public JComponent createComponent() {
                        return new JCheckBox("SDK Extra Option");
                    }

                    @Override
                    public void addSupport(@NotNull Module module, @NotNull ModifiableRootModel rootModel, @NotNull ModifiableModelsProvider modifiableModelsProvider) {

                    }


                };
            }

            @Override
            public boolean isEnabledForModuleType(@NotNull ModuleType type) {
                return true;
            }
        };
    }

    @Override
    public @NotNull @Nls(capitalization = Nls.Capitalization.Title) String getPresentableName() {
        return "WasmContract";
    }

    @Override
    public @NotNull Icon getIcon() {
        return IconLoader.getIcon("AllIcons.Actions.Compile", this.getClass());
    }
}
