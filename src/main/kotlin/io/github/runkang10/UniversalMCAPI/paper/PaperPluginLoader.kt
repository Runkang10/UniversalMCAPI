package io.github.runkang10.UniversalMCAPI.paper

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import io.github.runkang10.UniversalMCAPI.paper.console.SendConsoleMessage
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Class responsible to get Paper plugin information
 * stored in the `paper-plugin.yml` file.
 *
 * @property plugin Minecraft plugin instance.
 * @property logger UniversalMCAPI Logger
 */
class PaperPluginLoader(
    /**
     *
     */
    private var plugin: JavaPlugin,
    private var logger: SendConsoleMessage
) {
    private var configuration: YamlConfiguration? = null
    private var unknown: String = "unknown"

    init {
        loadConfiguration()
    }

    private fun loadConfiguration() {
        val inputStream: InputStream? = plugin.getResource("paper-plugin.yml")
        if (inputStream == null) {
            logger.warning("Could not find paper-plugin.yml!")
            return
        }
        configuration = YamlConfiguration.loadConfiguration(InputStreamReader(inputStream))
    }

    /**
     * Returns the plugin name specified in the configuration file,
     * or the fallback value "unknown" if not present.
     */
    fun getName(): String {
        return configuration
            ?.getString("name", unknown)
            .toString()
    }

    /**
     * Returns the plugin version from the configuration file,
     * or "unknown" if not present.
     */
    fun getVersion(): String {
        return configuration
            ?.getString("version", unknown)
            .toString()
    }

    /**
     * Returns the main class path of the plugin from the configuration file.
     */
    fun getMain(): String {
        return configuration
            ?.getString("main", unknown)
            .toString()
    }

    /**
     * Returns the API version of the plugin specified in the configuration file.
     */
    fun getApiVersion(): String {
        return configuration
            ?.getString("api-version", unknown)
            .toString()
    }

    /**
     * Returns the plugin load time (early, postworld, etc.),
     * or "unknown" if not specified.
     */
    fun getLoad(): String {
        return configuration
            ?.getString("load", unknown)
            .toString()
    }

    /**
     * Returns a list of plugin authors from the configuration file.
     */
    fun getAuthors(): MutableList<String>? {
        return configuration
            ?.getStringList("authors")
    }

    /**
     * Returns the description of the plugin from the configuration file.
     */
    fun getDescription(): String {
        return configuration
            ?.getString("description", unknown)
            .toString()
    }

    /**
     * Returns the website of the plugin, if specified in the configuration file.
     */
    fun getWebsite(): String {
        return configuration
            ?.getString("website", unknown)
            .toString()
    }
}