package com.dflch.dashboardemc.domain.usecases.turbiedad

import com.dflch.dashboardemc.domain.model.nivelrio.LecturasPlantas
import com.dflch.dashboardemc.domain.repository.turbiedad.TurbiedadP1Repository
import javax.inject.Inject

class GetTurbiedadP1UseCase @Inject constructor(
    private val repository: TurbiedadP1Repository
)  {
    suspend operator fun invoke(): Result<List<LecturasPlantas>> {
        return repository.getTurbiedadP1()
    }
}