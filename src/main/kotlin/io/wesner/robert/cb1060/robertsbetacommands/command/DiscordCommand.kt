package io.wesner.robert.cb1060.robertsbetacommands.command

import io.wesner.robert.cb1060.robertsbetacommands.BetaCommands
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class DiscordCommand() : Command {
    val plugin = BetaCommands.plugin

    override fun run(sender: CommandSender, args: Array<String>): Boolean {
        if (!plugin.configuration.getBoolean("commands.discord.enabled", false)) {
            return false
        }

        sender.sendMessage("${ChatColor.BLUE}Join the Discord on ${ChatColor.AQUA}${plugin.configuration.getString("commands.discord.link")}${ChatColor.BLUE}!")

        return true
    }
}
