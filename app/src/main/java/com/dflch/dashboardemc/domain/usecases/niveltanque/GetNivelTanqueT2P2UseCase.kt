package com.dflch.dashboardemc.domain.usecases.niveltanque

import com.dflch.dashboardemc.domain.model.nivelrio.LecturasPlantas
import com.dflch.dashboardemc.domain.repository.niveltanque.NivelTanqueT2P2Repository
import javax.inject.Inject

class GetNivelTanqueT2P2UseCase @Inject constructor(
    private val repository: NivelTanqueT2P2Repository
)  {
    suspend operator fun invoke(): Result<List<LecturasPlantas>> {
        return repository.getNivelTanqueT2P2()
    }
}