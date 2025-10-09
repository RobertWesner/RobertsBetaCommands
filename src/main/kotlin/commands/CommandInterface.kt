package io.wesner.robert.cb1060.robertsbetacommands.commands

import io.wesner.robert.cb1060.robertsbetacommands.BetaCommands
import org.bukkit.command.CommandSender

interface CommandInterface {
    fun run(sender: CommandSender, args: Array<String>): Boolean
}