package com.dflch.dashboardemc.domain.repository.turbiedad

import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas

interface TurbiedadP2Repository {
    suspend fun getTurbiedadP2(): Result<List<LecturasPlantas>>
}