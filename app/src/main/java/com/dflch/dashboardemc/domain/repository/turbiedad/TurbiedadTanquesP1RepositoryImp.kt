package com.dflch.dashboardemc.domain.repository.turbiedad

import com.dflch.dashboardemc.data.remote.api.turbiedad.TurbiedadP1ApiService
import com.dflch.dashboardemc.data.remote.api.turbiedad.TurbiedadTanqueP1ApiService
import com.dflch.dashboardemc.data.remote.mappers.toDomainModelList
import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TurbiedadTanquesP1RepositoryImp @Inject constructor(
    private val apiTurbiedadTanquesP1: TurbiedadTanqueP1ApiService
) : TurbiedadTanquesP1Repository {
    override suspend fun getTurbiedadTanquesP1(): Result<List<LecturasPlantas>> {
        return try {
            val response = apiTurbiedadTanquesP1.getTurbiedadTanquesP1()
            Result.success(response.toDomainModelList())
        } catch (e: SocketTimeoutException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}