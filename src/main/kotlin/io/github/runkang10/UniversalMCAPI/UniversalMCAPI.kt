package io.github.runkang10.UniversalMCAPI

import io.github.runkang10.UniversalMCAPI.common.TryCatchRunner
import org.bukkit.plugin.java.JavaPlugin
import io.github.runkang10.UniversalMCAPI.paper.PaperPluginLoader
import io.github.runkang10.UniversalMCAPI.paper.console.SendConsoleMessage

class UniversalMCAPI : JavaPlugin() {
    private lateinit var instance: UniversalMCAPI
    private lateinit var tryCatchRunner: TryCatchRunner
    private lateinit var serverLogger: SendConsoleMessage
    private lateinit var pluginInfo: PaperPluginLoader

    override fun onEnable() {
        super.onEnable()
        instance = this
        tryCatchRunner = TryCatchRunner(this)
        serverLogger = SendConsoleMessage(this, tryCatchRunner)
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

    fun getTryCatchRunner(): TryCatchRunner {
        return tryCatchRunner
    }

    fun getPluginInfo(): PaperPluginLoader {
        return pluginInfo
    }

    fun getServerLogger(): SendConsoleMessage {
        return serverLogger
    }
}
