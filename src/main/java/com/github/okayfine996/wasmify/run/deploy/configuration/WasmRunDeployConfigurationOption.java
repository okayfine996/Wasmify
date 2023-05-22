package com.github.okayfine996.wasmify.run.deploy.configuration;

import com.intellij.execution.configurations.LocatableRunConfigurationOptions;
import com.intellij.openapi.components.StoredProperty;

public class WasmRunDeployConfigurationOption extends LocatableRunConfigurationOptions {

    private final StoredProperty<String> extralParameter = string("").provideDelegate(this, "extralParameter");

    public String getExtralParameter() {
        return extralParameter.getValue(this);
    }

    public void setExtralParameter(String extralParameter) {
        this.extralParameter.setValue(this, extralParameter);
    }

}
