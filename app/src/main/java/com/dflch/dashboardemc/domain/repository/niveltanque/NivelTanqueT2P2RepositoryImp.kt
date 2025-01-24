package com.dflch.dashboardemc.domain.repository.niveltanque

import com.dflch.dashboardemc.data.remote.api.niveltanque.NivelTanqueT2P2ApiService
import com.dflch.dashboardemc.data.remote.mappers.toDomainModelList
import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NivelTanqueT2P2RepositoryImp @Inject constructor(
    private val apiNivelTanqueT2P2: NivelTanqueT2P2ApiService
) : NivelTanqueT2P2Repository {
    override suspend fun getNivelTanqueT2P2(): Result<List<LecturasPlantas>> {
        return try {
            val response = apiNivelTanqueT2P2.getNivelTanqueT2P2()
            Result.success(response.toDomainModelList())
        } catch (e: SocketTimeoutException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}