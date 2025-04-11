package com.dflch.dashboardemc.data.remote.dto

import com.google.gson.annotations.SerializedName

//Representa el JSON que recibes de la API.
data class IrcaResponseDto(
    @SerializedName("data") val data: DataIrcaDto,
    @SerializedName("messages") val messages: List<Any>,
    @SerializedName("succeeded") val succeeded: Boolean
)

data class DataIrcaDto(
    @SerializedName("sumIRCA") val sumIrca: String,
    @SerializedName("irca") val Irca: String,
    @SerializedName("samplesCount") val sumMuestras: String,
    @SerializedName("month") val mes: String
)





