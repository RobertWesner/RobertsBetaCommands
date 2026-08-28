package io.wesner.robert.cb1060.robertsbetacommands

import io.wesner.robert.cb1060.robertsbetacommands.command.Command
import org.bukkit.command.Command as BukkitCommand
import org.bukkit.command.CommandSender

class CommandHandler {
    private val commands: MutableMap<String, Command> = mutableMapOf()

    fun register(name: String, command: Command): CommandHandler {
        commands[name] = command

        return this
    }

    fun run(command: BukkitCommand, sender: CommandSender, args: Array<String>): Boolean {
        val name = command.name
        if (!commands.containsKey(name)) {
            return false
        }

        return commands[name]?.run(sender, args) ?: false
    }
}
