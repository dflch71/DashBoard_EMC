package com.dflch.dashboardemc.data.repository.network

import com.dflch.dashboardemc.data.repository.network.ConnectivityObserver
import com.dflch.dashboardemc.data.repository.network.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val connectivityObserver: ConnectivityObserver
) : NetworkRepository {

    override fun isNetworkAvailable(): Flow<Boolean> {
        return connectivityObserver.observeNetworkStatus()
    }

}
