package com.tortora.gerador.services

import com.tortora.gerador.representation.GeradorRepresentation

interface GeradorService {

    fun extractData(url: String)
    fun getBestGenerators(): List<GeradorRepresentation>

}