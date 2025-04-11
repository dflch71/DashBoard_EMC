package com.dflch.dashboardemc.domain.model.irca

data class IrcaResponse(
    val data: DataIrca,
    val messages: List<Any>,
    val succeeded: Boolean
)