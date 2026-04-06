package io.wesner.robert.cb1060.robertsbetacommands

import com.sun.net.httpserver.HttpServer
import io.wesner.robert.cb1060.robertsbetacommands.command.DiscordCommand
import io.wesner.robert.cb1060.robertsbetacommands.command.WebsiteCommand
import io.wesner.robert.cb1060.robertsbetacommands.listener.PlayerInteractListener
import io.wesner.robert.cb1060.robertsbetacommands.listener.PlayerJoinListener
import io.wesner.robert.cb1060.robertsbetacommands.task.AutoSaveTask
import io.wesner.robert.cb1060.robertsbetacommands.task.IntervalBroadcastTask
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.event.Event
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.net.InetSocketAddress
import java.util.logging.Logger


class BetaCommands : JavaPlugin() {
    val logger: Logger = Bukkit.getLogger()
    private lateinit var commandHandler: CommandHandler
    private var httpServer: HttpServer? = null

    override fun onDisable() {
        httpServer?.stop(0)
        Bukkit.getScheduler().cancelTasks(this)

        val description = description
        logger.info("Disabled " + description.name + ".")
    }

    override fun onEnable() {
        plugin = this

        ensureConfig()

        commandHandler = CommandHandler()
            .register("discord", DiscordCommand())
            .register("website", WebsiteCommand())

        registerEvent(Event.Type.PLAYER_JOIN, PlayerJoinListener(), Event.Priority.Normal)
        registerEvent(Event.Type.PLAYER_INTERACT, PlayerInteractListener(), Event.Priority.Highest)

        setUpTasks()
        setUpServerInformation()

        val description = description
        logger.info("Enabled " + description.name + " version " + description.version + ".")
    }

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): Boolean {
        return commandHandler.run(command, sender, args)
    }

    private fun ensureConfig() {
        if (!File(dataFolder, "config.yml").exists()) {
            dataFolder.mkdirs()
            val file = File(dataFolder, "config.yml")
            file.outputStream().use { fileOutputStream ->
                this::class.java.getResourceAsStream("/config.yml")?.copyTo(fileOutputStream)
            }
            configuration.load()
        }
    }

    private fun registerEvent(type: Event.Type, listener: Listener, priority: Event.Priority) {
        server.pluginManager.registerEvent(type, listener, priority, this)
    }

    private fun setUpTasks() {
        if (configuration.getBoolean("plugins.intervalBroadcast.enabled", false)) {
            val broadcastInterval = (configuration.getInt("plugins.intervalBroadcast.intervalSeconds", 600) * 20).toLong()
            Bukkit
                .getScheduler()
                .scheduleAsyncRepeatingTask(
                    this,
                    IntervalBroadcastTask(
                        configuration.getString("plugins.intervalBroadcast.prefix", ""),
                        configuration.getStringList("plugins.intervalBroadcast.messages", listOf()),
                    ),
                    broadcastInterval,
                    broadcastInterval,
                )
        }

        if (configuration.getBoolean("plugins.autoSave.enabled", false)) {
            val autoSaveInterval = (configuration.getInt("plugins.autoSave.intervalSeconds", 600) * 20).toLong()
            Bukkit
                .getScheduler()
                .scheduleAsyncRepeatingTask(
                    this,
                    AutoSaveTask(
                        configuration.getBoolean("plugins.autoSave.debug", false),
                    ),
                    autoSaveInterval,
                    autoSaveInterval,
                )
        }
    }

    private fun setUpServerInformation() {
        if (configuration.getBoolean("plugins.httpServerInformation.enabled", false)) {
            httpServer = HttpServer.create(InetSocketAddress(
                configuration.getInt("plugins.httpServerInformation.port", 1337)
            ), 0)
            httpServer!!.createContext("/players", ServerInformationHttpHandler(
                configuration.getInt("plugins.httpServerInformation.maxPlayers", 20)
            ))
            // creates a default executor
            httpServer!!.executor = null
            httpServer!!.start()
        }
    }

    companion object {
        lateinit var plugin: BetaCommands
    }
}
