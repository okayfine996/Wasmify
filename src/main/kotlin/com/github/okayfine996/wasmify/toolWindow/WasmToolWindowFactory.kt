package com.github.okayfine996.wasmify.toolWindow

import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import com.github.okayfine996.wasmify.MyBundle
import com.github.okayfine996.wasmify.listener.WasmServiceListener
import com.github.okayfine996.wasmify.services.MyProjectService
import com.github.okayfine996.wasmify.ui.contract.WasmContract
import com.intellij.icons.AllIcons
import com.intellij.icons.AllIcons.Toolbar
import com.intellij.openapi.actionSystem.DataProvider
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.LabeledComponent
import com.intellij.openapi.ui.VerticalFlowLayout
import com.intellij.openapi.ui.popup.IconButton
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.wm.impl.status.widget.StatusBarWidgetWrapper
import com.intellij.ui.components.JBComboBoxLabel
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextField
import com.intellij.ui.components.fields.ExpandableTextField
import com.intellij.ui.components.fields.ExtendableTextField
import com.intellij.ui.components.panels.VerticalLayout
import com.intellij.uiDesigner.core.GridLayoutManager
import com.intellij.util.ui.components.JBComponent
import com.sun.java.accessibility.util.AWTEventMonitor.addActionListener
import java.awt.CardLayout
import java.awt.Component
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.event.ActionListener
import java.awt.event.ComponentListener
import java.util.Arrays
import javax.swing.*
import javax.swing.event.AncestorEvent
import javax.swing.event.AncestorListener
import javax.swing.plaf.ScrollPaneUI


class WasmToolWindowFactory : ToolWindowFactory {
    init {
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    private val contentFactory = ContentFactory.SERVICE.getInstance()

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val wasmToolWindow = WasmToolWindow(toolWindow)
        map = wasmToolWindow
        val content = contentFactory.createContent(wasmToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)

    }

    override fun shouldBeAvailable(project: Project) = true


    companion object {
        lateinit var map: WasmToolWindow
    }

    fun getWasmToolWindow():WasmToolWindow {
        return map
    }

}

class WasmToolWindow(toolWindow: ToolWindow) {

    private val service = toolWindow.project.service<MyProjectService>()

    private val container = JPanel()
    private val map = HashMap<String,WasmContract>()
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




