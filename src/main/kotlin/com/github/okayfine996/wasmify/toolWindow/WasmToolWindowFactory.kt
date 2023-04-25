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
import com.github.okayfine996.wasmify.services.MyProjectService
import com.intellij.icons.AllIcons.Toolbar
import com.intellij.openapi.ui.popup.IconButton
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.wm.impl.status.widget.StatusBarWidgetWrapper
import com.sun.java.accessibility.util.AWTEventMonitor.addActionListener
import java.awt.event.ActionListener
import javax.swing.Icon
import javax.swing.JButton


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

    class WasmToolWindow(toolWindow: ToolWindow) {

        private val service = toolWindow.project.service<MyProjectService>()

        fun getContent() = JBPanel<JBPanel<*>>().apply {

            var deploy = JButton(IconLoader.getIcon("AllIcons.Actions.Compile", this.javaClass)).apply {
                setSize(20,14)
                addActionListener{
                    print("hello")
                }

            }

            val label = JBLabel(MyBundle.message("randomLabel", "?"))


           Toolbar()

            add(label)
            add(deploy)
        }
    }
}
