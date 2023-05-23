package com.github.okayfine996.wasmify.toolWindow

import com.github.okayfine996.wasmify.service.WasmService
import com.github.okayfine996.wasmify.service.WasmService.Signer
import com.github.okayfine996.wasmify.ui.signer.AddSignerDialog
import com.github.okayfine996.wasmify.ui.signer.SignerCell
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.DataProvider
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.ui.VerticalFlowLayout
import com.intellij.openapi.ui.popup.IconButton
import com.intellij.ui.components.IconLabelButton
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.panels.HorizontalLayout
import java.awt.Toolkit
import java.util.stream.Collectors
import javax.swing.JButton

class WasmToolWindowSignerPanel : SimpleToolWindowPanel(true) {
    private val addSigner = IconLabelButton(AllIcons.General.Add, {
        val dialog = AddSignerDialog()
        val dimension = Toolkit.getDefaultToolkit().screenSize
        val width = 600
        val height = 300
        dialog.setBounds((dimension.width - width) / 2, (dimension.height - height) / 2, width, height)
        dialog.pack()
        dialog.show()
    })


    var container = JBPanel<JBPanel<*>>(VerticalFlowLayout())

    private val wasmService = ApplicationManager.getApplication().getService(WasmService::class.java);

    init {
        toolbar = JBPanel<JBPanel<*>>().apply {
            layout = HorizontalLayout(10)
            add(addSigner)
        }



        wasmService.signerList.stream().map { SignerCell(it.name, it.value) }.forEach { container.add(it.content) }
        setContent(JBScrollPane(container))
    }

    fun updateList() {
        container.removeAll()
        wasmService.signerList.stream().map { SignerCell(it.name, it.value) }.forEach { container.add(it.content) }
    }
}