package com.dflch.dashboardemc.domain.usecases.turbiedad

import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
import com.dflch.dashboardemc.domain.repository.turbiedad.TurbiedadTanquesP1Repository
import javax.inject.Inject

class GetTurbiedadTanquesP1UseCase @Inject constructor(
    private val repository: TurbiedadTanquesP1Repository
)  {
    suspend operator fun invoke(): Result<List<LecturasPlantas>> {
        return repository.getTurbiedadTanquesP1()
    }
}