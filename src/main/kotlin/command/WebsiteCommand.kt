package io.wesner.robert.cb1060.robertsbetacommands.commands

import io.wesner.robert.cb1060.robertsbetacommands.BetaCommands
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class WebsiteCommand(val plugin: BetaCommands) : Command {
    override fun run(sender: CommandSender, args: Array<String>): Boolean {
        if (!this.plugin.configuration.getBoolean("commands.website.enabled", false)) {
            return false
        }

        sender.sendMessage("${ChatColor.BLUE}Check out the Website at ${ChatColor.GOLD}${plugin.configuration.getString("commands.website.link")}${ChatColor.BLUE}!")

        return true
    }
}