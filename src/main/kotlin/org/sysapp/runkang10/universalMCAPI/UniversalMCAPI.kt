package org.sysapp.runkang10.universalMCAPI

import org.bukkit.plugin.java.JavaPlugin
import org.sysapp.runkang10.universalMCAPI.paper.plugin.PaperPluginLoader
import org.sysapp.runkang10.universalMCAPI.paper.console.SendConsoleMessage

class UniversalMCAPI : JavaPlugin() {
    private lateinit var instance: UniversalMCAPI
    private lateinit var serverLogger: SendConsoleMessage
    private lateinit var pluginInfo: PaperPluginLoader

    override fun onEnable() {
        super.onEnable()
        instance = this
        serverLogger = SendConsoleMessage(this)
        pluginInfo = PaperPluginLoader(this, serverLogger)
        //
        serverLogger.info(
            "Successfully enabled " + pluginInfo.getName() + "!"
        )
    }

    override fun onDisable() {
        // Plugin shutdown logic
        serverLogger.info(
            "Successfully disabled " + pluginInfo.getName() + "!"
        )
    }

    fun getInstance(): UniversalMCAPI {
        return instance
    }

    fun getPluginInfo(): PaperPluginLoader {
        return pluginInfo
    }

    fun getServerLogger(): SendConsoleMessage {
        return serverLogger
    }
}
