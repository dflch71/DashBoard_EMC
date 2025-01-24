package com.dflch.dashboardemc.domain.repository.turbiedad

import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas

interface TurbiedadTanquesP2Repository {
    suspend fun getTurbiedadTanquesP2(): Result<List<LecturasPlantas>>
}