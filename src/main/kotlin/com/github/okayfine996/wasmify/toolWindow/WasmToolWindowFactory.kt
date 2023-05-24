package com.github.okayfine996.wasmify.toolWindow

import com.github.okayfine996.wasmify.cmwasm.results.MigrateResult
import com.github.okayfine996.wasmify.cmwasm.wasm.Fund
import com.github.okayfine996.wasmify.service.WasmService
import com.github.okayfine996.wasmify.ui.contract.WasmContract
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.VerticalFlowLayout
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.content.ContentFactory
import javax.swing.JPanel
import javax.swing.JScrollPane


class WasmToolWindowFactory : ToolWindowFactory {
    init {
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    private val contentFactory = ContentFactory.SERVICE.getInstance()


    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {

        toolWindow.contentManager.addContent(contentFactory.createContent(WasmToolWindowContractPanel(project),"Contract", false))
        toolWindow.contentManager.addContent(contentFactory.createContent(WasmToolWindowNetworkPanel(project), "Network", false))
        toolWindow.contentManager.addContent(contentFactory.createContent(WasmToolWindowSignerPanel(project), "Signer", false))
    }

    override fun shouldBeAvailable(project: Project) = true


    override fun init(toolWindow: ToolWindow) {
        super.init(toolWindow)
    }
}




