package com.dflch.dashboardemc.domain.repository.niveltanque

import com.dflch.dashboardemc.data.remote.api.niveltanque.NivelTanqueT1P1ApiService
import com.dflch.dashboardemc.data.remote.mappers.toDomainModelList
import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NivelTanqueT1P1RepositoryImp @Inject constructor(
    private val apiNivelTanqueT1P1: NivelTanqueT1P1ApiService
) : NivelTanqueT1P1Repository {
    override suspend fun getNivelTanqueT1P1(): Result<List<LecturasPlantas>> {
        return try {
            val response = apiNivelTanqueT1P1.getNivelTanqueT1P1()
            Result.success(response.toDomainModelList())
        } catch (e: SocketTimeoutException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}