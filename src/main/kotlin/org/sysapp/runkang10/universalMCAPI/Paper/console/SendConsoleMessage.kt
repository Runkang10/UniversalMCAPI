package org.sysapp.runkang10.universalMCAPI.paper.console

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.plugin.java.JavaPlugin

enum class SendConsoleMessageTypes {
    LOG,
    INFO,
    WARNING,
    ERROR
}

class SendConsoleMessage(plugin: JavaPlugin) {
    private var sendMessage = plugin.server.consoleSender
    private var alternativeSendMessage = plugin.logger

    fun info(msg: String) {
        return this.send(msg, SendConsoleMessageTypes.INFO)
    }

    fun warning(msg: String) {
        return this.send(msg, SendConsoleMessageTypes.WARNING)
    }

    fun error(msg: String) {
        return this.send(msg, SendConsoleMessageTypes.ERROR)
    }

    private fun send(msg: String, type: SendConsoleMessageTypes) {
        try {
            when (type) {
                SendConsoleMessageTypes.LOG -> {
                    sendMessage.sendMessage(
                        Component.text(
                            msg
                        )
                    )
                }

                SendConsoleMessageTypes.INFO -> {
                    sendMessage.sendMessage(
                        Component.text(
                            msg
                        )
                            .color(
                                NamedTextColor.GREEN
                            )
                    )
                }

                SendConsoleMessageTypes.WARNING -> {
                    sendMessage.sendMessage(
                        Component.text(
                            msg
                        )
                            .color(
                                NamedTextColor.GOLD
                            )
                    )
                }

                SendConsoleMessageTypes.ERROR -> {
                    sendMessage.sendMessage(
                        Component.text(
                            msg
                        )
                            .color(
                                NamedTextColor.DARK_RED
                            )
                    )
                }
            }
        } catch (e: Exception) {
            alternativeSendMessage.severe(
                "Unknown error while trying to log output: " +
                        e.message
            )

            alternativeSendMessage.severe(
                "Cause: " +
                        e.cause
            )

            alternativeSendMessage.severe(
                "Stack trace: " +
                        e.stackTraceToString()
            )

            alternativeSendMessage.severe(
                "Localized message: " +
                        e.localizedMessage
            )
        }
    }
}