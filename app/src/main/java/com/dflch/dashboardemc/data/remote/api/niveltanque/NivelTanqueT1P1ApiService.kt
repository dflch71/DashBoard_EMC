package com.dflch.dashboardemc.data.remote.api.niveltanque

import com.dflch.dashboardemc.core.utils.Constants.GET_PATH_AGUA_TRATADA_NIVEL_TANQUE1_P1
import com.dflch.dashboardemc.data.remote.dto.LecturaPlantasResponse
import retrofit2.http.GET

interface NivelTanqueT1P1ApiService {
    @GET(GET_PATH_AGUA_TRATADA_NIVEL_TANQUE1_P1)
    suspend fun getNivelTanqueT1P1(): LecturaPlantasResponse
}