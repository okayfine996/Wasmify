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
        val content = contentFactory.createContent(wasmToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)

    }

    override fun shouldBeAvailable(project: Project) = true


}

class WasmToolWindow(toolWindow: ToolWindow) {

    private val service = toolWindow.project.service<MyProjectService>()

    init {
        toolWindow.project.messageBus.connect().subscribe(
                WasmServiceListener.TOPIC, object : WasmServiceListener {
            override fun deployWasmEvent(contractAddress: String?) {
                println(contractAddress)
            }

            override fun update() {
                println("update")
            }

        }
        )
    }

    fun getContent(): JScrollPane {
        var p = JPanel()
        p.layout = VerticalFlowLayout()
        p.setSize(300,2000)
        p.add(WasmContract().rootPanel)
        p.add(WasmContract().rootPanel)
        p.add(WasmContract().rootPanel)
        p.add(WasmContract().rootPanel)
        p.add(WasmContract().rootPanel)

        var jsP = JScrollPane(p)
        jsP.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        jsP.isVisible = true
        return jsP
    }
}

class WasmToolWindowPanel : JPanel() {
    init {
//        var networkCombox = ComboBox<String>().apply {
//            addItem("OKC")
//            addItem("OKC2")
//            addItem("OKC3")
//            addItem("OKC4")
//            addItem("OKC5")
//        }
//
//        add(Box.createHorizontalBox().apply {
//            add(JBLabel("Network"))
//            add(networkCombox)
//        })
//
//
//        var deploy = JButton(AllIcons.Actions.Install).apply {
//            setSize(20, 14)
//            addActionListener {
//
//            }
//        }
//
//        var initMsgTextField = LabeledComponent.create(ExpandableTextField(), "Init Msg")
//
//        add(Box.createHorizontalBox().apply {
//            add(deploy)
//            add(initMsgTextField)
//        })



        var p = JPanel()
        p.layout = VerticalFlowLayout()
        p.setSize(300,2000)
        p.add(WasmContract().rootPanel)
        p.add(WasmContract().rootPanel)
        p.add(WasmContract().rootPanel)
        p.add(WasmContract().rootPanel)
        p.add(WasmContract().rootPanel)

        var jsP = JScrollPane(p)
        jsP.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        jsP.isVisible = true
        add(jsP)


    }
}




