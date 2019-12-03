package com.example

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.features.*
import io.ktor.jackson.jackson
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(DefaultHeaders)
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    data class City(val name: String, val state: String)

    routing {
        get("/") {
            call.respondText("Hello, World!")
        }
        get("/api/cities") {
            call.respond(
                arrayOf(
                    City("New York City", "NY"),
                    City("Los Angeles", "CA"),
                    City("Chicago", "IL"),
                    City("Houston", "TX"),
                    City("Phoenix", "AZ"),
                    City("Philadelphia", "PA")
                )
            )
        }
    }
}
