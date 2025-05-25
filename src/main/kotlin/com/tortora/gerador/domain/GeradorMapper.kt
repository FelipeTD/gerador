package com.tortora.gerador.domain

import com.tortora.gerador.representation.GeradorRepresentation

object GeradorMapper {

    fun toEntityList(representations: List<GeradorRepresentation>): List<GeradorEntity> {
        return representations.map { toEntity(it) }
    }

    fun toRepresentationList(entities: List<GeradorEntity>): List<GeradorRepresentation> {
        return entities.map { toRepresentation(it) }
    }

    private fun toEntity(representation: GeradorRepresentation): GeradorEntity {
        return GeradorEntity(
            nomeGerador = representation.nomeGerador,
            power = representation.power
        )
    }

    private fun toRepresentation(entity: GeradorEntity): GeradorRepresentation {
        return GeradorRepresentation(
            nomeGerador = entity.nomeGerador,
            power = entity.power
        )
    }

}