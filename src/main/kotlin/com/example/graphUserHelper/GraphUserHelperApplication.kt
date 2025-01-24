package com.example.graphUserHelper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GraphUserHelperApplication

fun main(args: Array<String>) {
	var context = runApplication<GraphUserHelperApplication>(*args)
	var service = context.getBean(GraphService::class.java)
	service.getUser("") //add your own user exoId
}
