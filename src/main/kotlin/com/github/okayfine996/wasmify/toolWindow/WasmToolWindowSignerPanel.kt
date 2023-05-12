package com.github.okayfine996.wasmify.toolWindow

import com.github.okayfine996.wasmify.service.WasmService
import com.github.okayfine996.wasmify.service.WasmService.Signer
import com.github.okayfine996.wasmify.ui.signer.AddSignerDialog
import com.github.okayfine996.wasmify.ui.signer.SignerCell
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.DataProvider
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.panels.HorizontalLayout
import java.awt.Toolkit
import javax.swing.JButton

class WasmToolWindowSignerPanel : SimpleToolWindowPanel(true) {
    private val addNetwork = JButton(AllIcons.General.Add).apply {
        addActionListener {
            val dialog = AddSignerDialog()
            val dimension = Toolkit.getDefaultToolkit().screenSize
            val width = 600
            val height = 300
            dialog.setBounds((dimension.width - width) / 2, (dimension.height - height) / 2, width, height)
            dialog.pack()
            dialog.show()
        }
    }

    private var jbList = JBList<Signer>().apply {
        setCellRenderer { list, value, index, isSelected, cellHasFocus ->  SignerCell(value.name,value.value).content}
    }

    private val wasmService = ApplicationManager.getApplication().getService(WasmService::class.java);

    init {
        toolbar = JBPanel<JBPanel<*>>().apply {
            layout = HorizontalLayout(10)
            add(addNetwork)
        }

        jbList.setListData(wasmService.signerList.toTypedArray())

        setContent(jbList)
    }

    fun updateList() {
        jbList.setListData(wasmService.signerList.toTypedArray())
    }
}