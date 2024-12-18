package com.dflch.dashboardemc.domain.repository.niveltanque

import com.dflch.dashboardemc.data.remote.api.niveltanque.NivelTanqueT1P2ApiService
import com.dflch.dashboardemc.data.remote.mappers.toDomainModelList
import com.dflch.dashboardemc.domain.model.nivelrio.LecturasPlantas
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NivelTanqueT1P2RepositoryImp @Inject constructor(
    private val apiNivelTanqueT1P2: NivelTanqueT1P2ApiService
) : NivelTanqueT1P2Repository {
    override suspend fun getNivelTanqueT1P2(): Result<List<LecturasPlantas>> {
        return try {
            val response = apiNivelTanqueT1P2.getNivelTanqueT1P2()
            Result.success(response.toDomainModelList())
        } catch (e: SocketTimeoutException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}