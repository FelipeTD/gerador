package com.tortora.gerador.domain

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Column
import java.io.Serializable
import java.math.BigDecimal

@Entity
@Table(name = "gerador")
data class GeradorEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: Int? = null,

    @Column(name = "nome_gerador", nullable = false)
    val nomeGerador: String,

    @Column(name = "power", nullable = false)
    val power: BigDecimal
) : Serializable