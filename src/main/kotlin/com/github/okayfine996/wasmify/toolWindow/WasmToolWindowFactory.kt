package com.github.okayfine996.wasmify.toolWindow

import com.github.okayfine996.wasmify.cmwasm.wasm.Fund
import com.github.okayfine996.wasmify.service.WasmService
import com.github.okayfine996.wasmify.ui.contract.WasmContract
import com.intellij.icons.AllIcons
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.VerticalFlowLayout
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.Label
import com.intellij.ui.content.ContentFactory
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JScrollPane


class WasmToolWindowFactory : ToolWindowFactory {
    init {
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    private val contentFactory = ContentFactory.SERVICE.getInstance()


    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val wasmToolWindow = WasmToolWindow(toolWindow)
        map = wasmToolWindow

        val wasmService = ApplicationManager.getApplication().getService(WasmService::class.java)

        val content = contentFactory.createContent(wasmToolWindow.getContent(), "Contract", false)
        wasmService.contractList.forEach {
            wasmToolWindow.addContract(WasmContract(it.contractAddress, it.chainName, it.signer).apply {
                setWasmContractActionListener(object : WasmContract.WasmContractActionListener {
                    override fun execute(signer: String?, contractAddress: String?, executeMsg: String?, chain: String?, fee: String?, gas: String?, funds: MutableList<Fund>?): String {
                        return wasmService.executeWasmContract(chain, contractAddress, signer, executeMsg, fee, gas, funds)
                    }

                    override fun query(contractAddress: String?, queryMsg: String?, chain: String?): String {
                        return wasmService.queryWasmContract(chain, contractAddress, queryMsg)
                    }

                    override fun migrate(signer: String?, contractAddress: String?, migrateMsg: String?, chain: String?, wasmFile: String?, fee: String?, gas: String?, funds: MutableList<Fund>?): String {
                        TODO("Not yet implemented")
                    }

                })
            })
        }

        toolWindow.contentManager.addContent(content)

        toolWindow.contentManager.addContent(contentFactory.createContent(networkPanel, "Network", false))

        toolWindow.contentManager.addContent(contentFactory.createContent(signerPanel, "Signer", false))
    }

    override fun shouldBeAvailable(project: Project) = true


    companion object {
        lateinit var map: WasmToolWindow
        var signerPanel = WasmToolWindowSignerPanel()
        var networkPanel = WasmToolWindowNetworkPanel(true, true)
    }

    fun getWasmToolWindow(): WasmToolWindow {
        return map
    }
}

class WasmToolWindow(toolWindow: ToolWindow) {
    private val container = JPanel()
    private val map = HashMap<String, WasmContract>()

    init {
        container.apply {
            layout = VerticalFlowLayout()
        }
    }

    fun getContent(): JScrollPane {
        return JBScrollPane(container)
    }

    fun addContract(contract: WasmContract) {
        map.put(contract.contractAddress, contract)
        container.add(contract.rootPanel)
    }
}




