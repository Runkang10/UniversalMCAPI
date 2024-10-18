package org.sysapp.runkang10.universalMCAPI.paper.console

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.plugin.java.JavaPlugin

enum class SendMessageTypes {
    LOG,
    INFO,
    WARNING,
    ERROR
}

class SendMessage(plugin: JavaPlugin) {
    private var sendMessage = plugin.server.consoleSender
    private var alternativeSendMessage = plugin.logger

    fun info(msg: String) {
        return this.send(msg, SendMessageTypes.INFO)
    }

    fun warning(msg: String) {
        return this.send(msg, SendMessageTypes.WARNING)
    }

    fun error(msg: String) {
        return this.send(msg, SendMessageTypes.ERROR)
    }

    private fun send(msg: String, type: SendMessageTypes) {
        try {
            when (type) {
                SendMessageTypes.LOG -> {
                    sendMessage.sendMessage(
                        Component.text(
                            msg
                        )
                    )
                }

                SendMessageTypes.INFO -> {
                    sendMessage.sendMessage(
                        Component.text(
                            msg
                        )
                            .color(
                                NamedTextColor.GREEN
                            )
                    )
                }

                SendMessageTypes.WARNING -> {
                    sendMessage.sendMessage(
                        Component.text(
                            msg
                        )
                            .color(
                                NamedTextColor.GOLD
                            )
                    )
                }

                SendMessageTypes.ERROR -> {
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