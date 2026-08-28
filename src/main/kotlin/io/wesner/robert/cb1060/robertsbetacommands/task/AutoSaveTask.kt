package io.wesner.robert.cb1060.robertsbetacommands.task

import io.wesner.robert.cb1060.robertsbetacommands.BetaCommands

class AutoSaveTask(val shouldLog: Boolean) : Runnable {
    val plugin = BetaCommands.plugin

    override fun run() {
        log("Automatic saving started.")
        plugin.server.savePlayers()
        plugin.server.worlds.forEach { it.save() }
        log("Automatic saving finished successfully.")
    }

    private fun log(message: String) {
        if (!shouldLog) return

        plugin.logger.info(message)
    }
}