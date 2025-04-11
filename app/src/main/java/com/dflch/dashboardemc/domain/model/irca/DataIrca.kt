package com.dflch.dashboardemc.domain.model.irca

data class DataIrca(
    val sumIRCA: String,
    val irca: String,
    val samplesCount: String,
    val month: String,

    // Nuevos campos (no vienen del JSON, los agregas tú)
    val year: Int,
    val startDate: String,
    val endDate: String
)