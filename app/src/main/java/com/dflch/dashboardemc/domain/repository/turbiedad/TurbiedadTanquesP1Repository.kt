package com.dflch.dashboardemc.domain.repository.turbiedad

import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas

interface TurbiedadTanquesP1Repository {
    suspend fun getTurbiedadTanquesP1(): Result<List<LecturasPlantas>>
}