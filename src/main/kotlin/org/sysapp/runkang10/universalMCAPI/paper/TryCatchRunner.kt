package org.sysapp.runkang10.universalMCAPI.paper

import org.bukkit.plugin.java.JavaPlugin
import org.sysapp.runkang10.universalMCAPI.paper.console.SendConsoleMessage

/**
 * A utility class to safely execute actions while handling exceptions.
 * This class provides a mechanism to execute a block of code and handle any exceptions that may occur.
 *
 * @param plugin The instance of the JavaPlugin that is being used.
 */
class TryCatchRunner(
    plugin: JavaPlugin
) {
    private var logger: SendConsoleMessage = SendConsoleMessage(plugin, this)

    /**
     * Executes the specified action while providing options for logging and exception handling.
     *
     * @param action The block of code to execute.
     * @param silent If true, suppresses error logging when an exception occurs.
     * @param log If true, logs the execution status of the action.
     * @param exceptionAction The action to perform in case of an exception.
     *                        Defaults to logging the error details.
     */
    fun execute(
        action: () -> Unit,
        silent: Boolean = false,
        log: Boolean = false,
        exceptionAction: (e: Exception) -> Unit = { e ->
            if (!silent) {
                logger.error("===================================================================")
                logger.defaultError("  Unknown error while trying to log output: ${e.message}")
                logger.defaultError("  Cause: ${e.cause}")
                logger.defaultError("  Stack trace: ${e.stackTraceToString()}")
                logger.defaultError("  Localized message: ${e.localizedMessage}")
                logger.error("===================================================================")
            }
        }
    ) {
        try {
            if (log) {
                logger.defaultInfo("Running action: `${action}` ...")
                logger.defaultInfo("Action `${action}` hashcode: `${action.hashCode()}`.")
            }
            action()
            if (log) logger.info("Action: `${action}` with hashcode `${action.hashCode()}` successfully executed.")
        } catch (e: Exception) {
            exceptionAction(e)
        }
    }
}