package org.sysapp.runkang10.universalMCAPI.folia

import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.sysapp.runkang10.universalMCAPI.UniversalMCAPI
import org.sysapp.runkang10.universalMCAPI.paper.console.SendMessage

class FoliaAPI(private var plugin: JavaPlugin) {
    private var isfolia: Boolean = this.isFolia()
    private var logger: SendMessage = UniversalMCAPI().getServerLogger()
    private lateinit var globalRegionScheduler: GlobalRegionScheduler

    fun init() {
        this.run {
            globalRegionScheduler = plugin.server.globalRegionScheduler
            logger.info("Folia configuration has been set up successfully.")
        }
    }

    private fun isFolia(): Boolean {
        return try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer")
            true
        } catch (e: Exception) {
            false
        }
    }

    fun runFoliaCode(action: () -> Unit, async: Boolean = false): Boolean {
        return if (isfolia) {
            if (async) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
                    action.invoke()
                })
            } else {
                action.invoke()
            }
            true
        } else {
            logger.warning("=================================================")
            logger.warning("  Cannot use `$action` on this server.")
            logger.warning("")
            logger.warning("  Please check if your server uses Folia.")
            logger.warning("=================================================")
            false
        }
    }
}