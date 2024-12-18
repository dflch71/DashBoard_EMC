package com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dflch.dashboardemc.domain.model.nivelrio.LecturasPlantas
import com.dflch.dashboardemc.domain.usecases.turbiedad.GetTurbiedadP1UseCase
import com.dflch.dashboardemc.domain.usecases.turbiedad.GetTurbiedadP2UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TurbiedadViewModel @Inject constructor(
    private val getTurbiedadP1UseCase: GetTurbiedadP1UseCase,
    private val getTurbiedadP2UseCase: GetTurbiedadP2UseCase,
): ViewModel() {


    private val _uiState = MutableStateFlow<UiState<List<LecturasPlantas>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<LecturasPlantas>>> = _uiState

    init {
        fetchTurbiedadP1()
        fetchTurbiedadP2()
    }

    private fun fetchTurbiedadP1() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = getTurbiedadP1UseCase()
            _uiState.value = when {
                result.isSuccess -> UiState.Success(result.getOrNull() ?: emptyList())
                result.isFailure -> UiState.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
                else ->  UiState.Error("Error desconocido")
            }
        }
    }

    private fun fetchTurbiedadP2() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = getTurbiedadP2UseCase()
            _uiState.value = when {
                result.isSuccess -> UiState.Success(result.getOrNull() ?: emptyList())
                result.isFailure -> UiState.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
                else ->  UiState.Error("Error desconocido")
            }
        }
    }

    fun refreshDataTurbiedadP1() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val data = getTurbiedadP1UseCase()
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


