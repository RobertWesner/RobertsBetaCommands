package io.wesner.robert.cb1060.robertsbetacommands.commands

import org.bukkit.command.CommandSender

interface Command {
    fun run(sender: CommandSender, args: Array<String>): Boolean
}