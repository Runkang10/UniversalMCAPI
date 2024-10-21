package org.sysapp.runkang10.universalMCAPI.paper

import org.bukkit.plugin.java.JavaPlugin
import org.sysapp.runkang10.universalMCAPI.paper.console.SendConsoleMessage

class TruCatchRunner(
    private val plugin: JavaPlugin,
    private val logger: SendConsoleMessage
) {
    fun execute(
        action: () -> Unit,
        exceptionAction: () -> Unit
    ) {

    }
    
}