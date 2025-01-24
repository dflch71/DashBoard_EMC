package com.dflch.dashboardemc.domain.repository.turbiedad

import com.dflch.dashboardemc.data.remote.api.turbiedad.TurbiedadP1ApiService
import com.dflch.dashboardemc.data.remote.api.turbiedad.TurbiedadTanqueP1ApiService
import com.dflch.dashboardemc.data.remote.api.turbiedad.TurbiedadTanqueP2ApiService
import com.dflch.dashboardemc.data.remote.mappers.toDomainModelList
import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TurbiedadTanquesP2RepositoryImp @Inject constructor(
    private val apiTurbiedadTanquesP2: TurbiedadTanqueP2ApiService
) : TurbiedadTanquesP2Repository {
    override suspend fun getTurbiedadTanquesP2(): Result<List<LecturasPlantas>> {
        return try {
            val response = apiTurbiedadTanquesP2.getTurbiedadTanquesP2()
            Result.success(response.toDomainModelList())
        } catch (e: SocketTimeoutException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}