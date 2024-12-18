package com.dflch.dashboardemc.data.remote.api.niveltanque

import com.dflch.dashboardemc.core.utils.Constants.GET_PATH_AGUA_TRATADA_NIVEL_TANQUE2_P2
import com.dflch.dashboardemc.data.remote.dto.LecturaPlantasResponse
import retrofit2.http.GET

interface NivelTanqueT2P2ApiService {
    @GET(GET_PATH_AGUA_TRATADA_NIVEL_TANQUE2_P2)
    suspend fun getNivelTanqueT2P2(): LecturaPlantasResponse
}