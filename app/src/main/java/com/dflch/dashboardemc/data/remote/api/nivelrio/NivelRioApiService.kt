package com.dflch.dashboardemc.data.remote.api.nivelrio

import com.dflch.dashboardemc.core.utils.Constants.GET_PATH_HISTORICO_NIVEL_RIO
import com.dflch.dashboardemc.data.remote.dto.LecturaPlantasResponse
import retrofit2.http.GET

interface NivelRioApiService {
    @GET(GET_PATH_HISTORICO_NIVEL_RIO)
    suspend fun getNivelRio(): LecturaPlantasResponse
}