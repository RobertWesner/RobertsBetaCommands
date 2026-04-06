package io.wesner.robert.cb1060.robertsbetacommands.listener

import io.wesner.robert.cb1060.robertsbetacommands.BetaCommands
import org.bukkit.Material
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerListener

class PlayerInteractListener : PlayerListener() {
    val plugin = BetaCommands.plugin

    override fun onPlayerInteract(event: PlayerInteractEvent) {
        if (
            plugin.configuration.getBoolean("plugins.onePlayerSleep.enabled", false)
            && event.action == Action.RIGHT_CLICK_BLOCK
            && event.clickedBlock.type.id == Material.BED_BLOCK.id
        ) {
            val world = plugin.server.getWorld(event.clickedBlock.world.name)
            if (world.time in 12001..<23999) {
                world.time = 0
            }
        }

        if (
            plugin.configuration.getBoolean("plugins.persistentSoil.enabled", false)
            && event.action == Action.PHYSICAL
            && event.clickedBlock.type.id == Material.SOIL.id
        ) {
            event.isCancelled = true
        }
    }
}
