package com.github.okayfine996.wasmify.toolWindow

import com.github.okayfine996.wasmify.cmwasm.results.MigrateResult
import com.github.okayfine996.wasmify.cmwasm.wasm.Fund
import com.github.okayfine996.wasmify.service.WasmService
import com.github.okayfine996.wasmify.ui.contract.Network
import com.github.okayfine996.wasmify.ui.contract.WasmContract
import com.github.okayfine996.wasmify.ui.network.AddNetworkDialog
import com.intellij.icons.AllIcons
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.ui.VerticalFlowLayout
import com.intellij.ui.components.IconLabelButton
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.panels.HorizontalLayout
import java.awt.Toolkit

/**
 *
 *@author finefine at: 2023/5/23 18:20
 *
 */
class WasmToolWindowContractPanel : SimpleToolWindowPanel(true) {
    val container = JBPanel<JBPanel<*>>(VerticalFlowLayout())

    private val wasmService = ApplicationManager.getApplication().getService(WasmService::class.java);

    init {
        wasmService.contractList.map {
            WasmContract(it.contractAddress, it.chainName, it.signer)
        }.forEach { it ->
            initEvent(it)
            container.add(it.rootPanel)
        }

        setContent(JBScrollPane(container))
    }

    fun updatePanel() {
        container.removeAll()
        wasmService.contractList.map {
            WasmContract(it.contractAddress, it.chainName, it.signer)
        }.forEach { it ->
            initEvent(it)
            container.add(it.rootPanel)
        }
    }


    fun initEvent(contract: WasmContract) {
        contract.apply {
            setOnRemoveListener { contractAddress ->
                wasmService.removeContract(contractAddress)
                container.remove(this.rootPanel)
                container.updateUI()
            }

            setWasmContractActionListener(object : WasmContract.WasmContractActionListener {
                override fun execute(signer: String?, contractAddress: String?, executeMsg: String?, chain: String?, fee: String?, gas: String?, funds: MutableList<Fund>?): String {
                    return wasmService.executeWasmContract(chain, contractAddress, signer, executeMsg, fee, gas, funds)
                }

                override fun query(contractAddress: String?, queryMsg: String?, chain: String?): String {
                    return wasmService.queryWasmContract(chain, contractAddress, queryMsg)
                }

                override fun migrate(signer: String?, contractAddress: String?, migrateMsg: String?, chain: String?, wasmFile: String?, fee: String?, gas: String?, fund: Int): MigrateResult? {
                    return wasmService.updateWasmContract(chain, contractAddress, wasmFile, signer, migrateMsg, fee, gas, fund)
                }
            })
        }
    }
}