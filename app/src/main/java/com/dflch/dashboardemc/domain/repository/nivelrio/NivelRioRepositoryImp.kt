package com.dflch.dashboardemc.domain.repository.nivelrio

import com.dflch.dashboardemc.data.remote.api.nivelrio.NivelRioApiService
import com.dflch.dashboardemc.data.remote.mappers.toDomainModel
import com.dflch.dashboardemc.data.remote.mappers.toDomainModelList
import com.dflch.dashboardemc.domain.model.nivelrio.LecturasPlantas
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NivelRioRepositoryImp @Inject constructor(
    private val apiNivelRio: NivelRioApiService
) : NivelRioRepository {
    override suspend fun getNivelRio(): Result<List<LecturasPlantas>> {
        return try {
            val response = apiNivelRio.getNivelRio()
            Result.success(response.toDomainModelList())
        } catch (e: SocketTimeoutException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getStatus(): String {
        try {
            val response = apiNivelRio.getNivelRio()
            return Result.success(response.toDomainModel()).getOrNull().toString()
        } catch (e: Exception) {
            return e.toString()
        }
    }
}