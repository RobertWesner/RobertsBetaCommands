package io.wesner.robert.cb1060.robertsbetacommands.task

import io.wesner.robert.cb1060.robertsbetacommands.BetaCommands

class AutoSaveTask(val plugin: BetaCommands, val shouldLog: Boolean) : Runnable {
    override fun run() {
        this.log("Automatic saving started.")
        this.plugin.server.savePlayers()
        this.plugin.server.worlds.forEach { it.save() }
        this.log("Automatic saving finished successfully.")
    }

    private fun log(message: String) {
        if (!this.shouldLog) return

        this.plugin.logger.info(message)
    }
}