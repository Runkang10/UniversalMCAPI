package org.sysapp.runkang10.universalMCAPI

import org.bukkit.plugin.java.JavaPlugin
import org.sysapp.runkang10.universalMCAPI.paper.plugin.PaperPluginLoader
import org.sysapp.runkang10.universalMCAPI.paper.console.SendMessage

class UniversalMCAPI : JavaPlugin() {
    private lateinit var instance: UniversalMCAPI
    private lateinit var serverLogger: SendMessage
    private lateinit var pluginInfo: PaperPluginLoader

    override fun onEnable() {
        super.onEnable()
        instance = this
        serverLogger = SendMessage(this)
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

    fun getServerLogger(): SendMessage {
        return serverLogger
    }
}
