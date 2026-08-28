package io.wesner.robert.cb1060.robertsbetacommands

// https://stackoverflow.com/questions/3732109/simple-http-server-in-java-using-only-java-se-api

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import java.io.IOException

class ServerInformationHttpHandler(val maxPlayers: Int) : HttpHandler {
    val plugin = BetaCommands.plugin

    @Throws(IOException::class)
    override fun handle(exchange: HttpExchange) {
        val response = "{\"count\":${plugin.server.onlinePlayers.size},\"max\":${maxPlayers}}"

        exchange.responseHeaders.add("Content-Type", "application/json")
        exchange.sendResponseHeaders(200, response.length.toLong())

        val os = exchange.responseBody
        os.write(response.toByteArray())
        os.close()
    }
}