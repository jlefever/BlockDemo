package com.example

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.features.*
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

    data class City(val name: String, val state: String)

    routing {
        static {
            defaultResource("index.html", "web")
            resources("web")
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
