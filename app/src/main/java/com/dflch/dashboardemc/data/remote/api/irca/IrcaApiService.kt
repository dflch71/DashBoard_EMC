package com.dflch.dashboardemc.data.remote.api.irca

import com.dflch.dashboardemc.core.utils.Constants.GET_PATH_IRCA
import com.dflch.dashboardemc.data.remote.dto.IrcaResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IrcaApiService {
    @GET(GET_PATH_IRCA)
    suspend fun getIRCA(
        @Query("InitialDate") FechaIni: String,
        @Query("EndDate") FechaFin: String
    ): Response<IrcaResponseDto>
}