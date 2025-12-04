package com.tim.hrstats

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HrstatsApplication

fun main(args: Array<String>) {
	runApplication<HrstatsApplication>(*args)
    println("http://localhost:8080/swagger-ui.html")
}
