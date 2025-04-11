package com.dflch.dashboardemc.domain.repository.irca

import com.dflch.dashboardemc.domain.model.irca.DataIrca

interface IrcaRepository {
    suspend fun getIrca(FechaIni: String, FechaFin: String): Result<DataIrca>
}