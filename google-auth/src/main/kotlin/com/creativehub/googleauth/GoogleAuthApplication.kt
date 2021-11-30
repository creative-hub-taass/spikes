package com.creativehub.googleauth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GoogleAuthApplication

fun main(args: Array<String>) {
	runApplication<GoogleAuthApplication>(*args)
}
