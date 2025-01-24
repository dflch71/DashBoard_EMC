package com.dflch.dashboardemc.domain.usecases.turbiedad

import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
import com.dflch.dashboardemc.domain.repository.turbiedad.TurbiedadP2Repository
import javax.inject.Inject

class GetTurbiedadP2UseCase @Inject constructor(
    private val repository: TurbiedadP2Repository
)  {
    suspend operator fun invoke(): Result<List<LecturasPlantas>> {
        return repository.getTurbiedadP2()
    }
}