package com.github.okayfine996.wasmify.toolWindow

import com.github.okayfine996.wasmify.service.WasmService
import com.github.okayfine996.wasmify.ui.signer.AddSignerDialog
import com.intellij.icons.AllIcons
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.SimpleToolWindowPanel
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
    private val wasmService = ApplicationManager.getApplication().getService(WasmService::class.java);

    init {
        toolbar = JBPanel<JBPanel<*>>().apply {
            layout = HorizontalLayout(10)
            add(addNetwork)
        }
    }
}