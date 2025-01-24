package com.dflch.dashboardemc.domain.repository.niveltanque

import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas

interface NivelTanqueT1P1Repository {
    suspend fun getNivelTanqueT1P1(): Result<List<LecturasPlantas>>
}