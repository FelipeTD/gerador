package com.tortora.gerador.controller

import com.tortora.gerador.representation.GeradorRepresentation
import com.tortora.gerador.services.GeradorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ExtractorController(
    private val service: GeradorService
) {

    @PostMapping("/extract")
    fun extractData(@RequestParam("url") url: String): ResponseEntity<Unit> {
        service.extractData(url)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/ranking")
    fun bestGenerators(): ResponseEntity<List<GeradorRepresentation>> {
        return ResponseEntity.ok(service.getBestGenerators())
    }

}