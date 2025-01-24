package com.dflch.dashboardemc.domain.usecases.turbiedad

import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
import com.dflch.dashboardemc.domain.repository.turbiedad.TurbiedadTanquesP2Repository
import javax.inject.Inject

class GetTurbiedadTanquesP2UseCase @Inject constructor(
    private val repository: TurbiedadTanquesP2Repository
)  {
    suspend operator fun invoke(): Result<List<LecturasPlantas>> {
        return repository.getTurbiedadTanquesP2()
    }
}