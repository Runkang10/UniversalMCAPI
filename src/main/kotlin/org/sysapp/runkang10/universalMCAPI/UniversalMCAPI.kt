package org.sysapp.runkang10.universalMCAPI

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.plugin.java.JavaPlugin
import org.sysapp.runkang10.universalMCAPI.Paper.PaperPluginLoader

class UniversalMCAPI : JavaPlugin() {
    private var instance: UniversalMCAPI = TODO()
    private var pluginInfo: PaperPluginLoader

    override fun onEnable() {
        super.onEnable()
        instance = this
        pluginInfo = PaperPluginLoader(this)
        // Plugin startup logic
        this.server.consoleSender.sendMessage(
            Component.text(
                "Successfully enabled " + pluginInfo.getName() + "!"
            )
                .color(NamedTextColor.GREEN)
        )
    }

    fun getInstance(): UniversalMCAPI {
        return instance;
    }

    override fun onDisable() {
        // Plugin shutdown logic
        this.server.consoleSender.sendMessage(
            Component.text(
                "Successfully disabled " + pluginInfo.getName() + "!"
            )
                .color(NamedTextColor.RED)
        )
    }
}
