package com.github.okayfine996.wasmify.toolWindow

import com.github.okayfine996.wasmify.model.Network
import com.github.okayfine996.wasmify.service.WasmService
import com.intellij.icons.AllIcons
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.panels.HorizontalLayout
import java.awt.Color
import javax.swing.JButton

class WasmToolWindowNetworkPanel(vertical: Boolean, borderless: Boolean) : SimpleToolWindowPanel(vertical, borderless) {
    private val addNetwork = JButton(AllIcons.General.Add)
    private val wasmService = ApplicationManager.getApplication().getService(WasmService::class.java);

    init {
        toolbar = JBPanel<JBPanel<*>>().apply {
            layout = HorizontalLayout(10)
            add(addNetwork)
        }
        var jList = JBList<Network>(wasmService.networkList)
        
        jList.setCellRenderer { list, value, index, isSelected, cellHasFocus ->
            com.github.okayfine996.wasmify.ui.contract.Network(value.name,value.chainId,value.url,"",value.denom).content.apply {
                if (isSelected) {
//                    background = Color.gray
//                    foreground = Color.gray
                }
            }
        }

        jList.dragEnabled = true


        setContent(jList)
    }
}