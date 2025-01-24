package com.dflch.dashboardemc.domain.usecases.niveltanque

import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
import com.dflch.dashboardemc.domain.repository.niveltanque.NivelTanqueT1P1Repository
import javax.inject.Inject

class GetNivelTanqueT1P1UseCase @Inject constructor(
    private val repository: NivelTanqueT1P1Repository
)  {
    suspend operator fun invoke(): Result<List<LecturasPlantas>> {
        return repository.getNivelTanqueT1P1()
    }
}