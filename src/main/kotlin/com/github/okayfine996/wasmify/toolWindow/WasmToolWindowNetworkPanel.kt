package com.github.okayfine996.wasmify.toolWindow

import com.github.okayfine996.wasmify.model.Network
import com.github.okayfine996.wasmify.service.WasmService
import com.github.okayfine996.wasmify.ui.network.AddNetworkDialog
import com.github.okayfine996.wasmify.ui.signer.AddSignerDialog
import com.intellij.icons.AllIcons
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.panels.HorizontalLayout
import java.awt.Color
import java.awt.Toolkit
import javax.swing.JButton

class WasmToolWindowNetworkPanel(vertical: Boolean, borderless: Boolean) : SimpleToolWindowPanel(vertical, borderless) {
    private val addNetwork = JButton(AllIcons.General.Add).apply {
        addActionListener {
            val dialog = AddNetworkDialog()
            val dimension = Toolkit.getDefaultToolkit().screenSize
            val width = 600
            val height = 300
            dialog.setBounds((dimension.width - width) / 2, (dimension.height - height) / 2, width, height)
            dialog.pack()
            dialog.show()
        }
    }

    private var jList = JBList<Network>().apply {
        setCellRenderer { list, value, index, isSelected, cellHasFocus ->
            com.github.okayfine996.wasmify.ui.contract.Network(value.name,value.chainId,value.restURL,value.explorerURL,value.denom).content.apply {
            }
        }
    }
    private val wasmService = ApplicationManager.getApplication().getService(WasmService::class.java);

    init {
        toolbar = JBPanel<JBPanel<*>>().apply {
            layout = HorizontalLayout(10)
            add(addNetwork)
        }

        setContent(jList)
        jList.setListData(wasmService.networkList.toTypedArray())
    }

    fun updateJBList() {
        jList.setListData(wasmService.networkList.toTypedArray())
    }
}