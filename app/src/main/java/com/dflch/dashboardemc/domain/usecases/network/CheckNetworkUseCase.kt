package com.dflch.dashboardemc.domain.usecases.network

import com.dflch.dashboardemc.data.repository.network.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckNetworkUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return networkRepository.isNetworkAvailable()
    }
}