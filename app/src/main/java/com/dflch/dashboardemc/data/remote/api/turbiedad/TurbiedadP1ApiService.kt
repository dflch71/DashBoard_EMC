package com.dflch.dashboardemc.data.remote.api.turbiedad

import com.dflch.dashboardemc.core.utils.Constants.GET_PATH_AGUA_CRUDA_TURBIEDAD_P1
import com.dflch.dashboardemc.data.remote.dto.LecturaPlantasResponse
import retrofit2.http.GET

interface TurbiedadP1ApiService {
    @GET(GET_PATH_AGUA_CRUDA_TURBIEDAD_P1)
    suspend fun getTurbiedadP1(): LecturaPlantasResponse
}