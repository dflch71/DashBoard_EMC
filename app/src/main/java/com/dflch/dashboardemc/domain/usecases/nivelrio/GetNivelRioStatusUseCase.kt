package com.dflch.dashboardemc.domain.usecases.nivelrio

import com.dflch.dashboardemc.domain.repository.nivelrio.NivelRioRepository
import javax.inject.Inject

class GetNivelRioStatusUseCase @Inject constructor(
    private val repository: NivelRioRepository
)  {
    suspend operator fun invoke(): String {
        return repository.getStatus()
    }
}