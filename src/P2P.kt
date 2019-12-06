package com.example

import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.response.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import java.time.LocalDateTime

interface Logger {
    fun logInfo(message: String)
    fun logWarning(message: String)
    fun logError(message: String)
}

class ConsoleLogger : Logger {
    override fun logInfo(message: String) = log("[INFO] ", message)
    override fun logWarning(message: String) = log("[WARN] ", message)
    override fun logError(message: String) = log("[ERROR]", message)

    private fun log(severity: String, message: String) {
        println("$severity ${LocalDateTime.now()} $message")
    }
}

class P2P(private val peers: List<String>, private val client: HttpClient, private val logger: Logger) {
    // Is this async? I don't know kotlin well enough.
    suspend fun broadcast(block: Block) {
        logger.logInfo("Broadcasting block ${block.index} to all peers...")
        for (peer in peers) {
            val url = "$peer/p2p/receive"
            logger.logInfo("Attempting to post to $url...")
            try {
                client.post<HttpResponse> {
                    url(url)
                    contentType(ContentType.Application.Json)
                    body = block
                }
            } catch (e: ClientRequestException) {
                logger.logWarning("Could not post to $peer")
                // If we notice our peer is not responding with a success, we could remove them as a peer.
            }
        }
    }
}
