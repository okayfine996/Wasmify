package com.github.okayfine996.wasmify.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.github.okayfine996.wasmify.MyBundle
import com.github.okayfine996.wasmify.cmwasm.wasm.WasmClient
import com.github.okayfine996.wasmify.model.Network
import java.util.concurrent.ConcurrentHashMap

@Service(Service.Level.APP)
class MyProjectService(project: Project) {


    init {
        thisLogger().info(MyBundle.message("projectService", project.name))
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    fun getRandomNumber() = (1..100).random()
}
