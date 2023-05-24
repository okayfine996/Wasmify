package com.github.okayfine996.wasmify.utils;

import com.github.okayfine996.wasmify.toolWindow.WasmToolWindowContractPanel;
import com.github.okayfine996.wasmify.toolWindow.WasmToolWindowNetworkPanel;
import com.github.okayfine996.wasmify.toolWindow.WasmToolWindowSignerPanel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

/**
 * @author finefine at: 2023/5/24 16:57
 */
public class ToolWindowUtil {
    public static WasmToolWindowSignerPanel getSignerPanel(Project project) {
        return (WasmToolWindowSignerPanel) getPanel(project, "Signer");
    }

    public static WasmToolWindowContractPanel getContractPanel(Project project) {
        return (WasmToolWindowContractPanel) getPanel(project, "Contract");
    }

    public static WasmToolWindowNetworkPanel getNetworkPanel(Project project) {
        return (WasmToolWindowNetworkPanel) getPanel(project, "Network");
    }


    public static SimpleToolWindowPanel getPanel(Project project, String displayName) {
        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Wasmify");
        return (SimpleToolWindowPanel) toolWindow.getContentManager().findContent(displayName).getComponent();
    }
}
