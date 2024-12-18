package com.dflch.dashboardemc.domain.repository.turbiedad

import com.dflch.dashboardemc.data.remote.api.turbiedad.TurbiedadP1ApiService
import com.dflch.dashboardemc.data.remote.mappers.toDomainModelList
import com.dflch.dashboardemc.domain.model.nivelrio.LecturasPlantas
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TurbiedadP1RepositoryImp @Inject constructor(
    private val apiTurbiedadP1: TurbiedadP1ApiService
) : TurbiedadP1Repository {
    override suspend fun getTurbiedadP1(): Result<List<LecturasPlantas>> {
        return try {
            val response = apiTurbiedadP1.getTurbiedadP1()
            Result.success(response.toDomainModelList())
        } catch (e: SocketTimeoutException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}