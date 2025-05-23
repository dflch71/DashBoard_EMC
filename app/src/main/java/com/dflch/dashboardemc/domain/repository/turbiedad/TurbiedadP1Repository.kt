package com.dflch.dashboardemc.domain.repository.turbiedad

import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas

interface TurbiedadP1Repository {
    suspend fun getTurbiedadP1(): Result<List<LecturasPlantas>>
}