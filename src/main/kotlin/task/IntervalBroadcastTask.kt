package io.wesner.robert.cb1060.robertsbetacommands.task

import io.wesner.robert.cb1060.robertsbetacommands.BetaCommands
import org.bukkit.ChatColor

class IntervalBroadcastTask(
    val plugin: BetaCommands,
    val prefix: String,
    var messages: List<String>
) : Runnable {
    var current = 0

    init {
        if (this.messages.isEmpty()) {
            this.messages = listOf("${ChatColor.RED}Please configure the broadcasts correctly or disable them.")
        }
    }

    override fun run() {
        for (line in this.messages[current].trim('\n').split("\n")) {
            plugin.server.broadcastMessage(this.prefix + line)
        }

        if (++current >= this.messages.size) {
            current = 0;
        }
    }
}