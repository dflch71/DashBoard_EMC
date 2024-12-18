package com.dflch.dashboardemc.domain.usecases.niveltanque

import com.dflch.dashboardemc.domain.model.nivelrio.LecturasPlantas
import com.dflch.dashboardemc.domain.repository.niveltanque.NivelTanqueT1P2Repository
import javax.inject.Inject

class GetNivelTanqueT1P2UseCase @Inject constructor(
    private val repository: NivelTanqueT1P2Repository
)  {
    suspend operator fun invoke(): Result<List<LecturasPlantas>> {
        return repository.getNivelTanqueT1P2()
    }
}