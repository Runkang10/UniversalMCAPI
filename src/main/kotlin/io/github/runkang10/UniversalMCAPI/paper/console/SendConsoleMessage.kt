package io.github.runkang10.UniversalMCAPI.paper.console

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.plugin.java.JavaPlugin
import io.github.runkang10.UniversalMCAPI.common.TryCatchRunner

enum class SendConsoleMessageTypes {
    INFO,
    WARNING,
    ERROR
}

class SendConsoleMessage(
    plugin: JavaPlugin,
    private var tryCatchRunner: TryCatchRunner
) {
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

    fun defaultInfo(msg: String) {
        return alternativeSendMessage.info(msg)
    }

    fun defaultWarning(msg: String) {
        return alternativeSendMessage.warning(msg)
    }

    fun defaultError(msg: String) {
        return alternativeSendMessage.severe(msg)
    }

    private fun send(msg: String, type: SendConsoleMessageTypes) {

        tryCatchRunner.execute(
            action = {
                when (type) {
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
            }
        )
    }
}