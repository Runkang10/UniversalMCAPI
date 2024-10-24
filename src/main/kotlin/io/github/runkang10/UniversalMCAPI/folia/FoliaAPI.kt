package io.github.runkang10.UniversalMCAPI.folia

import io.github.runkang10.UniversalMCAPI.common.TryCatchRunner
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import io.github.runkang10.UniversalMCAPI.UniversalMCAPI
import io.github.runkang10.UniversalMCAPI.paper.console.SendConsoleMessage

class FoliaAPI(private var plugin: JavaPlugin) {
    private val isfolia: Boolean = this.isFolia()
    private val trycatchrunner: TryCatchRunner = UniversalMCAPI().getTryCatchRunner()
    private val logger: SendConsoleMessage = UniversalMCAPI().getServerLogger()
    private lateinit var globalRegionScheduler: GlobalRegionScheduler

    fun init() {
        if (isfolia)
            globalRegionScheduler = plugin.server.globalRegionScheduler
            logger.info("Detected Folia server.")
    }

    private fun isFolia(): Boolean {
        return try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer")
            true
        } catch (e: Exception) {
            false
        }
    }

    fun runFoliaCode(action: () -> Unit, async: Boolean = false) {
        trycatchrunner.execute(
            action = {
                return@execute if (isfolia) {
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
        )
    }
}