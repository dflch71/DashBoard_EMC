package com.dflch.dashboardemc.ui.presentation.network.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dflch.dashboardemc.data.repository.network.NetworkStatusTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val networkStatusTracker: NetworkStatusTracker
) : ViewModel() {

    // Estado de la red observable
    val networkStatus: StateFlow<Boolean> = networkStatusTracker.networkStatus

    // Init para inicializar la verificación de red desde el arranque
    init {
        observeNetworkStatus()
    }

    private fun observeNetworkStatus() {
        // Opcional si necesitas hacer algo más aquí
        viewModelScope.launch {
            networkStatus.collect() { isConnected ->
                if (isConnected) {
                    // Hacer algo si hay una red disponible
                    // Sincronizar datos

                }  else {
                    // Hacer algo si no hay una red disponible

                }
            }
        }
    }

    // Desregistrar el callback cuando el ViewModel se limpie (opcional)
    override fun onCleared() {
        super.onCleared()
        networkStatusTracker.unregisterCallback()
    }
}

