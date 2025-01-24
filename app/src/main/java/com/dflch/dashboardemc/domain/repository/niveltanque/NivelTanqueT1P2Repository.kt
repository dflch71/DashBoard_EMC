package com.dflch.dashboardemc.domain.repository.niveltanque

import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas

interface NivelTanqueT1P2Repository {
    suspend fun getNivelTanqueT1P2(): Result<List<LecturasPlantas>>
}