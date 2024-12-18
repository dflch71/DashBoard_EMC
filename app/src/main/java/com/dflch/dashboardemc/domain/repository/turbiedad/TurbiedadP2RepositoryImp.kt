package com.dflch.dashboardemc.domain.repository.turbiedad

import com.dflch.dashboardemc.data.remote.api.turbiedad.TurbiedadP2ApiService
import com.dflch.dashboardemc.data.remote.mappers.toDomainModelList
import com.dflch.dashboardemc.domain.model.nivelrio.LecturasPlantas
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TurbiedadP2RepositoryImp @Inject constructor(
    private val apiTurbiedadP2: TurbiedadP2ApiService
) : TurbiedadP2Repository {
    override suspend fun getTurbiedadP2(): Result<List<LecturasPlantas>> {
        return try {
            val response = apiTurbiedadP2.getTurbiedadP2()
            Result.success(response.toDomainModelList())
        } catch (e: SocketTimeoutException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}