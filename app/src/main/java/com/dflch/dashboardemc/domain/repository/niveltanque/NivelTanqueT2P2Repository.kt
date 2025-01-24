package com.dflch.dashboardemc.domain.repository.niveltanque

import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas

interface NivelTanqueT2P2Repository {
    suspend fun getNivelTanqueT2P2(): Result<List<LecturasPlantas>>
}