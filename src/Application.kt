package com.example

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.features.*
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.*
import io.ktor.jackson.jackson
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    var blockchain = createNewChain()

    routing {
        // Serve frontend javascript application
        static {
            defaultResource("index.html", "web/build")
            resources("web/build")
        }
        post("/user/mineBlock") {
            var block = createNewBlock(blockchain.last(), "")
            addBlockToChain(block, blockchain)
            // TODO: Broadcast this block to all peers
            call.respond(HttpStatusCode.Created)
        }
        get("/p2p/blockchain") {
            // TODO: Should also support only returning last x blocks
            call.respond(blockchain)
        }
        post("/p2p/receiveBlock") {
            // TODO: If this block meets certain conditions, then we will pull the entire blockchain of this peer.
            // (Instead of pulling the entire chain, we could just pull the most recent x blocks and check that against ours.)
            // If that blockchain meets certain conditions, then we will replace our blockchain with theirs.
        }
    }
}
