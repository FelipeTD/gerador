package com.tortora.gerador

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GeradorApplication

fun main(args: Array<String>) {
	runApplication<GeradorApplication>(*args)
}
