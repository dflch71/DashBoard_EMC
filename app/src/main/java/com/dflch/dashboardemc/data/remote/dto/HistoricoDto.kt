package com.dflch.dashboardemc.data.remote.dto

import com.google.gson.annotations.SerializedName

//Representa el JSON que recibes de la API.
data class LecturaPlantasResponse(
    @SerializedName("status") val status: String,
    @SerializedName("historico") val historico: List<HistoricoDto>
)

data class HistoricoDto(
    @SerializedName("hora") val hora: Int,
    @SerializedName("fecha") val fecha: String,
    @SerializedName("lectura") val lectura: Double
)



