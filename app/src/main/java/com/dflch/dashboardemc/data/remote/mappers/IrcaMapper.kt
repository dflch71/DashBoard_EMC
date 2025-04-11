package com.dflch.dashboardemc.data.remote.mappers

import com.dflch.dashboardemc.data.remote.dto.DataIrcaDto
import com.dflch.dashboardemc.data.remote.dto.IrcaResponseDto
import com.dflch.dashboardemc.domain.model.irca.DataIrca

fun DataIrcaDto.toDomainModel(): DataIrca {
    return DataIrca(
        sumIRCA = this.Irca ?: "",
        irca = this.Irca ?: "",
        samplesCount = this.sumMuestras ?: "",
        month = this.mes ?: "",
        year = 0,
        startDate = "",
        endDate = ""
    )
}

fun IrcaResponseDto.toDomainModel(): DataIrca { return this.data.toDomainModel() }




