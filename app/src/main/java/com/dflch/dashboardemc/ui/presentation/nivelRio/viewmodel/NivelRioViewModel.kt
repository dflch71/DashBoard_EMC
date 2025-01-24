package com.dflch.dashboardemc.ui.presentation.nivelRio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
import com.dflch.dashboardemc.domain.usecases.nivelrio.GetNivelRioStatusUseCase
import com.dflch.dashboardemc.domain.usecases.nivelrio.GetNivelRioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NivelRioViewModel @Inject constructor(
    private val getNivelRioUseCase: GetNivelRioUseCase,
    private val getNivelRioStatusUseCase: GetNivelRioStatusUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<LecturasPlantas>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<LecturasPlantas>>> = _uiState

    private val _status = MutableStateFlow("")
    val status: StateFlow<String> = _status

    init { fetchNivelRio() }

    fun fetchNivelRio() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = getNivelRioUseCase()
            _uiState.value = when {
                result.isSuccess -> UiState.Success(result.getOrNull() ?: emptyList())
                result.isFailure -> UiState.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
                else ->  UiState.Error("Error desconocido")
            }
        }

        viewModelScope.launch {
            _status.value = getNivelRioStatusUseCase()
        }
    }

    fun refreshDataNivelRio() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val data = getNivelRioUseCase()
                _uiState.value = UiState.Success(data.getOrNull() ?: emptyList())
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}


