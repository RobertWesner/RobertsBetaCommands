package io.wesner.robert.cb1060.robertsbetacommands

import io.wesner.robert.cb1060.robertsbetacommands.commands.CommandInterface
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class CommandHandler {
    private val commands: MutableMap<String, CommandInterface> = mutableMapOf()

    fun register(name: String, command: CommandInterface): CommandHandler {
        this.commands[name] = command

        return this
    }

    fun run(command: Command, sender: CommandSender, args: Array<String>): Boolean {
        val name = command.name
        if (!this.commands.containsKey(name)) {
            return false
        }

        return this.commands[name]?.run(sender, args) ?: false
    }
}