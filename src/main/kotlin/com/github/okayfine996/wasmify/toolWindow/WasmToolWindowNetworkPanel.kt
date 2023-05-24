package com.github.okayfine996.wasmify.toolWindow

import com.github.okayfine996.wasmify.service.WasmService
import com.github.okayfine996.wasmify.ui.contract.Network
import com.github.okayfine996.wasmify.ui.network.AddNetworkDialog
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

class WasmToolWindowNetworkPanel(project: Project) : SimpleToolWindowPanel(true) {
    val project: Project

    private val addNetwork = IconLabelButton(AllIcons.General.Add, {
        val dialog = AddNetworkDialog(project)
        val dimension = Toolkit.getDefaultToolkit().screenSize
        val width = 600
        val height = 300
        dialog.setBounds((dimension.width - width) / 2, (dimension.height - height) / 2, width, height)
        dialog.pack()
        dialog.show()
    })

    var container = JBPanel<JBPanel<*>>(VerticalFlowLayout())

    private val wasmService:WasmService

    init {
        this.project = project
        wasmService = project.getService(WasmService::class.java);

        toolbar = JBPanel<JBPanel<*>>().apply {
            layout = HorizontalLayout(10)
            add(addNetwork)
        }

        wasmService.networkList.stream().map { network ->
            Network(network.name, network.chainId, network.restURL, network.explorerURL, network.denom).apply {
                setOnRemoveNetworkListener { network ->
                    wasmService.removeNetwork(network)
                    container.remove(this.content)
                    container.updateUI()
                }
            }
        }.forEach {
            container.add(it.content)
        }
        setContent(JBScrollPane(container))
    }

    fun updateJBList() {
        container.removeAll()
        wasmService.networkList.stream().map { network ->
            Network(network.name, network.chainId, network.restURL, network.explorerURL, network.denom).apply {
                setOnRemoveNetworkListener { network ->
                    wasmService.removeNetwork(network)
                    container.remove(this.content)
                    container.updateUI()
                }
            }
        }.forEach {
            container.add(it.content)
        }
    }
}