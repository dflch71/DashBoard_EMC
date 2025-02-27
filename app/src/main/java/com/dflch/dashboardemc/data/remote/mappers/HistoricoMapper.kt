package com.dflch.dashboardemc.data.remote.mappers

import com.dflch.dashboardemc.data.remote.dto.HistoricoDto
import com.dflch.dashboardemc.data.remote.dto.LecturaPlantasResponse
import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas

fun HistoricoDto.toDomainModel(): LecturasPlantas {
    return LecturasPlantas(
        hora = this.hora ?: 0,
        fecha = this.fecha ?: "",
        lectura = this.lectura ?: 0.0
    )
}

fun LecturaPlantasResponse.toDomainModelList(): List<LecturasPlantas> {
    return this.historico.map { it.toDomainModel() }
}

fun LecturaPlantasResponse.toDomainModel(): String{
    return this.status ?: ""
}

