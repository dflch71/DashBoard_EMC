package com.dflch.dashboardemc.domain.repository.nivelrio

import com.dflch.dashboardemc.domain.model.nivelrio.LecturasPlantas

interface NivelRioRepository {
    suspend fun getNivelRio(): Result<List<LecturasPlantas>>
    suspend fun getStatus(): String
}