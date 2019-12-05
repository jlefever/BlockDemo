package com.example

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.features.*
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.*
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.request.receiveOrNull
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

        route("ui") {
            post("add") {
                var block = createNewBlock(blockchain.last(), "")
                addBlockToChain(block, blockchain)
                // TODO: Broadcast this block to all peers
                call.respond(HttpStatusCode.Created, block)
            }
            post("update") {
                val block = call.receive<Block>()

                if (block.index < 0 || block.index > blockchain.last.index) {
                    call.respond(HttpStatusCode.BadRequest)
                }

                // We might want to consider the case where our blockchain is not indexed at 0
                blockchain[block.index] = block
                call.respond(HttpStatusCode.OK)
            }
            post("mine") {
                // TODO: Implement mine
                call.respond(HttpStatusCode.OK)
            }
            post("broadcast") {
                // TODO: Implement broadcast
                call.respond(HttpStatusCode.OK)
            }
            get("is_valid_chain") {
                call.respond(isValidChain(blockchain))
            }
            get("is_valid_new_block/{index}") {
                val index = call.parameters["index"]?.toInt()!!
                call.respond(isValidNewBlock(blockchain[index], blockchain[index - 1]))
            }
            get("is_hash_correct/{index}") {
                val index = call.parameters["index"]?.toInt()!!
                call.respond(isHashCorrect(blockchain[index]))
            }
        }

        route("p2p") {
            get("blockchain") {
                // TODO: Should also support only returning last x blocks
                call.respond(blockchain)
            }
            post("receive") {
                // TODO: If this block meets certain conditions, then we will pull the entire blockchain of this peer.
                // (Instead of pulling the entire chain, we could just pull the most recent x blocks and check that against ours.)
                // If that blockchain meets certain conditions, then we will replace our blockchain with theirs.
            }
        }
    }
}
