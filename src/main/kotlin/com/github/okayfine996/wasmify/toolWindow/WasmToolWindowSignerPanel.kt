package com.github.okayfine996.wasmify.toolWindow

import com.github.okayfine996.wasmify.service.WasmService
import com.github.okayfine996.wasmify.ui.signer.AddSignerDialog
import com.github.okayfine996.wasmify.ui.signer.SignerCell
import com.intellij.icons.AllIcons
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.ui.VerticalFlowLayout
import com.intellij.ui.components.IconLabelButton
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.panels.HorizontalLayout
import java.awt.Toolkit

class WasmToolWindowSignerPanel(project: Project) : SimpleToolWindowPanel(true) {
    private val addSigner = IconLabelButton(AllIcons.General.Add, {
        val dialog = AddSignerDialog(project)
        val dimension = Toolkit.getDefaultToolkit().screenSize
        val width = 600
        val height = 300
        dialog.setBounds((dimension.width - width) / 2, (dimension.height - height) / 2, width, height)
        dialog.pack()
        dialog.show()
    })


    var container = JBPanel<JBPanel<*>>(VerticalFlowLayout())

    private val wasmService = project.getService(WasmService::class.java);

    init {
        toolbar = JBPanel<JBPanel<*>>().apply {
            layout = HorizontalLayout(10)
            add(addSigner)
        }



        wasmService.signerList.stream().map {
            SignerCell(it.name, it.value).apply {
                setOnRemoveListener { name ->
                    wasmService.removeSigner(name)
                    container.remove(this.content)
                    container.updateUI()
                }
            }
        }.forEach { container.add(it.content) }
        setContent(JBScrollPane(container))
    }

    fun updateList() {
        container.removeAll()
        wasmService.signerList.stream().map {
            SignerCell(it.name, it.value).apply {
                setOnRemoveListener { name ->
                    wasmService.removeSigner(name)
                    container.remove(this.content)
                    container.updateUI()
                }
            }
        }.forEach { container.add(it.content) }
    }
}