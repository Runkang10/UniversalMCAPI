package org.sysapp.runkang10.universalMCAPI.Paper

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import org.sysapp.runkang10.universalMCAPI.UniversalMCAPI
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Class responsible for loading and managing the Paper plugin configuration
 * using the `paper-plugin.yml` file.
 */
class PaperPluginLoader(private val plugin: JavaPlugin) {

    // Instance of UniversalMCAPI to handle server console output
    private val instance: UniversalMCAPI = UniversalMCAPI().getInstance()

    // Variable to hold the loaded configuration from the YML file
    private var configuration: YamlConfiguration? = null

    // Fallback value in case data is not found in the configuration file
    private val unknown: String = "unknown"

    /**
     * Initialization method to load the `paper-plugin.yml` file.
     * Uses `InputStream` to read the file and load it into a `YamlConfiguration`.
     */
    fun init(plugin: JavaPlugin) {
        try {
            // Load the configuration file from the plugin jar
            val stream: InputStream? = plugin.getResource("paper-plugin.yml")

            // If the file is found, convert it to a YamlConfiguration
            configuration = YamlConfiguration.loadConfiguration(InputStreamReader(stream))
        } catch (e: Exception) {
            // If an exception occurs, send an error message to the server console
            instance.server.consoleSender.sendMessage(
                Component.text("Could not call `PaperPluginLoader` class: " + e.stackTraceToString())
                    .color(NamedTextColor.RED)
                    .decorate(TextDecoration.BOLD)
            )
        }
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