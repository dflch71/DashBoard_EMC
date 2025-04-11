package com.dflch.dashboardemc.domain.usecases.irca

import com.dflch.dashboardemc.domain.model.irca.DataIrca
import com.dflch.dashboardemc.domain.repository.irca.IrcaRepository
import javax.inject.Inject

class GetIrcaUseCase @Inject constructor(
    private val repository: IrcaRepository
)  {
    suspend operator fun invoke( FechaIni: String, FechaFin: String ): Result<DataIrca> {
        return repository.getIrca(FechaIni, FechaFin)
    }
}