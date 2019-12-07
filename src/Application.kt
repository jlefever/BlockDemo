package com.example

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.response.*
import io.ktor.features.*
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.*
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.routing.*
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    val peers = System.getenv("BDPEERS")?.split(";") ?: emptyList()

    val client = HttpClient(Apache) {
        install(JsonFeature) { serializer = JacksonSerializer() }
    }

    val logger = ConsoleLogger()

    val p2p = P2P(peers, client, logger)

    val blockchain = Blockchain(createNewChain())

    routing {
        // Serve frontend javascript application
        static {
            defaultResource("index.html", "web/build")
            resources("web/build")
        }

        route("ui") {
            post("add") {
                var block = createNewBlock(blockchain.chain.last(), "")

                if (addBlockToChain(block, blockchain.chain)) {
                    call.respond(HttpStatusCode.Created, block)
                } else {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        "Cannot add block. Chain is not in valid state."
                    )
                }
            }
            post("update") {
                val block = call.receive<Block>()

                if (!isInRange(blockchain.chain, block.index)) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    // We might want to consider the case where our blockchain is not indexed at 0
                    blockchain.chain[block.index] = block
                    call.respond(HttpStatusCode.OK)
                }
            }
            post("mine/{index}") {
                val index = call.parameters["index"]?.toInt()!!

                if (!isInRange(blockchain.chain, index)) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    val block = blockchain.chain[index]

                    if (mineBlock(block)) {
                        call.respond(block)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Unable to mine block.")
                    }
                }
            }
            post("broadcast") {
                p2p.broadcast(blockchain.chain)
                call.respond(HttpStatusCode.OK)
            }
            get("is_valid_chain") {
                call.respond(isValidChain(blockchain.chain))
            }
            get("is_valid_new_block/{index}") {
                val index = call.parameters["index"]?.toInt()!!

                if (!isInRange(blockchain.chain, index)) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    call.respond(isValidNewBlock(blockchain.chain[index], blockchain.chain[index - 1]))
                }
            }
            get("is_mined/{index}") {
                val index = call.parameters["index"]?.toInt()!!

                if (!isInRange(blockchain.chain, index)) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    call.respond(isMined(blockchain.chain[index]))
                }
            }
            get("peers") {
                call.respond(peers)
            }
        }

        route("p2p") {
            get("blockchain") {
                call.respond(blockchain)
            }
            post("receive") {
                val receivedChain = call.receive<LinkedList<Block>>()
                logger.logInfo("Received chain of length ${receivedChain.size}")
                blockchain.chain = LinkedList(p2p.receive(receivedChain, blockchain.chain))
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
