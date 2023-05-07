package com.github.okayfine996.wasmify.run.deploy.configuration;

import com.intellij.execution.configurations.LocatableRunConfigurationOptions;
import com.intellij.openapi.components.StoredProperty;

public class WasmRunDeployConfigurationOption extends LocatableRunConfigurationOptions {

    private final StoredProperty<String> restApi = string("http://127.0.0.1:8545").provideDelegate(this, "restApi");
    private final StoredProperty<String> privateKey = string("").provideDelegate(this,"privateKey");
    private final StoredProperty<String> denom = string("").provideDelegate(this,"denom");
    private final StoredProperty<String> instantiateMsg = string("").provideDelegate(this,"instantiateMsg");

    public String getRestApi() {
        return restApi.getValue(this);
    }

    public void setRestApi(String restApi) {
        this.restApi.setValue(this, restApi);
    }

    public String getPrivateKey () {
        return privateKey.getValue(this);
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey.setValue(this,privateKey);
    }

    public String getDenom() {
        return this.denom.getValue(this);
    }

    public void setDenom(String denom) {
        this.denom.setValue(this,denom);
    }

    public String getInstantiateMsg() {
        return this.instantiateMsg.getValue(this);
    }

    public void setInstantiateMsg(String instantiateMsg) {
        this.instantiateMsg.setValue(this,instantiateMsg);
    }

}
