package com.dflch.dashboardemc.data.repository.network

import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    fun isNetworkAvailable(): Flow<Boolean>
}