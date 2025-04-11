package com.dflch.dashboardemc.domain.repository.irca

import com.dflch.dashboardemc.data.remote.api.irca.IrcaApiService
import com.dflch.dashboardemc.data.remote.dto.IrcaResponseDto
import com.dflch.dashboardemc.data.remote.mappers.toDomainModel
import com.dflch.dashboardemc.data.remote.mappers.toDomainModelList
import com.dflch.dashboardemc.domain.model.irca.DataIrca
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IrcaRepositoryImp @Inject constructor(
    private val apiIrca: IrcaApiService
) : IrcaRepository {

    override suspend fun getIrca(FechaIni: String, FechaFin: String): Result<DataIrca> {
        /*return try {
            val response = apiIrca.getIRCA(FechaIni, FechaFin)
            Result.success(response. ())
        } catch (e: SocketTimeoutException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }*/

        return try {
            val response = apiIrca.getIRCA(FechaIni, FechaFin)
            if (response.isSuccessful) {
                val dataResponse: IrcaResponseDto? = response.body()
                dataResponse?.let {
                    /*
                    val domainData = DataIrca(
                        sumIRCA = dataResponse.data.sumIrca,
                        irca = dataResponse.data.Irca,
                        samplesCount = dataResponse.data.sumMuestras,
                        month = dataResponse.data.mes
                    )*/
                    val domainData = dataResponse.toDomainModel()
                    Result.success(domainData)
                } ?: Result.failure (Exception("Response body is null"))
            } else {
                Result.failure(Exception("Request failed with code: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}