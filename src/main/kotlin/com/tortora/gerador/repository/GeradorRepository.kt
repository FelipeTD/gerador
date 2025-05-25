package com.tortora.gerador.repository

import com.tortora.gerador.domain.GeradorEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GeradorRepository : JpaRepository<GeradorEntity, Int> {

    fun findTop5ByOrderByPowerDesc(): List<GeradorEntity>

}