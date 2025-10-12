package io.wesner.robert.cb1060.robertsbetacommands.task

import io.wesner.robert.cb1060.robertsbetacommands.BetaCommands
import org.bukkit.ChatColor

class IntervalBroadcastTask(
    val prefix: String,
    var messages: List<String>
) : Runnable {
    val plugin = BetaCommands.plugin

    var current = 0

    init {
        if (messages.isEmpty()) {
            messages = listOf("${ChatColor.RED}Please configure the broadcasts correctly or disable them.")
        }
    }

    override fun run() {
        for (line in messages[current].trim('\n').split("\n")) {
            plugin.server.broadcastMessage(prefix + line)
        }

        if (++current >= messages.size) {
            current = 0;
        }
    }
}