package io.wesner.robert.cb1060.robertsbetacommands

import io.wesner.robert.cb1060.robertsbetacommands.commands.DiscordCommand
import io.wesner.robert.cb1060.robertsbetacommands.commands.WebsiteCommand
import io.wesner.robert.cb1060.robertsbetacommands.listener.PlayerJoinListener
import io.wesner.robert.cb1060.robertsbetacommands.listener.PlayerInteractListener
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.event.Event
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.logging.Logger

class BetaCommands : JavaPlugin() {
    val logger: Logger = Logger.getLogger("Minecraft")
    private lateinit var commandHandler: CommandHandler

    private val playerJoinListener = PlayerJoinListener(this)
    private val playerInteractListener = PlayerInteractListener(this)

    override fun onDisable() {
        val description = this.description
        logger.info("Disabled " + description.name + ".")
    }

    override fun onEnable() {
        this.ensureConfig()

        this.commandHandler = CommandHandler()
            .register("discord", DiscordCommand(this))
            .register("website", WebsiteCommand(this))

        this.registerEvent(Event.Type.PLAYER_JOIN, this.playerJoinListener, Event.Priority.Normal)
        this.registerEvent(Event.Type.PLAYER_INTERACT, this.playerInteractListener, Event.Priority.Normal)

        val description = this.description
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

    private fun ensureConfig() {
        if (!File(this.dataFolder, "config.yml").exists()) {
            this.dataFolder.mkdirs()
            val file = File(this.dataFolder, "config.yml")
            file.outputStream().use { fileOutputStream ->
                this::class.java.getResourceAsStream("/config.yml")?.copyTo(fileOutputStream)
            }
            this.configuration.load()
        }
    }

    private fun registerEvent(type: Event.Type, listener: Listener, priority: Event.Priority) {
        this.server.pluginManager.registerEvent(type, listener, priority, this)
    }
}