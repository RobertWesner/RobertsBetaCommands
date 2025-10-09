package io.wesner.robert.cb1060.robertsbetacommands.listener

import io.wesner.robert.cb1060.robertsbetacommands.BetaCommands
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerListener

class PlayerJoinListener(val plugin: BetaCommands) : PlayerListener() {
    override fun onPlayerJoin(event: PlayerJoinEvent) {
        if (plugin.configuration.getBoolean("plugins.joinPlayerMessage.enabled", false)) {
            val message = plugin.configuration.getString("plugins.joinPlayerMessage.message") ?: ""
            for (line in message.split("\n")) {
                event.player.sendMessage(line)
            }
        }
    }
}