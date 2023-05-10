package com.github.okayfine996.wasmify.states;

import com.intellij.openapi.components.PersistentStateComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WasmContractState implements PersistentStateComponent<WasmContractState> {
    @Override
    public @Nullable WasmContractState getState() {
        return null;
    }

    @Override
    public void loadState(@NotNull WasmContractState state) {

    }
}
