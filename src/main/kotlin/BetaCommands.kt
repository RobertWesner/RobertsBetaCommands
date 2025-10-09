package io.wesner.robert.cb1060.robertsbetacommands

import io.wesner.robert.cb1060.robertsbetacommands.commands.DiscordCommand
import io.wesner.robert.cb1060.robertsbetacommands.commands.WebsiteCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.logging.Logger

class BetaCommands : JavaPlugin() {
    val logger: Logger = Logger.getLogger("Minecraft")
    private var commandHandler: CommandHandler = CommandHandler()

    override fun onDisable() {
        // reset command handler
        this.commandHandler = CommandHandler()

        val description = this.description;
        logger.info("Disabled " + description.name + ".");
    }

    override fun onEnable() {
        if (!File(this.dataFolder, "config.yml").exists()) {
            this.dataFolder.mkdirs()
            val file = File(this.dataFolder, "config.yml")
            file.outputStream().use { fileOutputStream ->
                this::class.java.getResourceAsStream("/config.yml")?.copyTo(fileOutputStream)
            }
            this.configuration.load()
        }

        this.commandHandler
            .register("discord", DiscordCommand(this))
            .register("website", WebsiteCommand(this))

        val description = this.description;
        logger.info("Enabled " + description.name + " version " + description.version + ".")
    }

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): Boolean {
        return this.commandHandler.run(command, sender, args)
    }
}