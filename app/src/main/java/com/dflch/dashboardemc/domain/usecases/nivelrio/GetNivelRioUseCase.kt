package com.dflch.dashboardemc.domain.usecases.nivelrio

import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
import com.dflch.dashboardemc.domain.repository.nivelrio.NivelRioRepository
import javax.inject.Inject

class GetNivelRioUseCase @Inject constructor(
    private val repository: NivelRioRepository
)  {
    suspend operator fun invoke(): Result<List<LecturasPlantas>> {
        return repository.getNivelRio()
    }
}