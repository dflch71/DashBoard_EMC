package com.dflch.dashboardemc.ui.presentation.niveltanque.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dflch.dashboardemc.domain.model.nivelrio.LecturasPlantas
import com.dflch.dashboardemc.domain.usecases.niveltanque.GetNivelTanqueT1P1UseCase
import com.dflch.dashboardemc.domain.usecases.niveltanque.GetNivelTanqueT1P2UseCase
import com.dflch.dashboardemc.domain.usecases.niveltanque.GetNivelTanqueT2P2UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NivelTanqueViewModel @Inject constructor(
    private val getNivelTanqueT1P1UseCase: GetNivelTanqueT1P1UseCase,
    private val getNivelTanqueT1P2UseCase: GetNivelTanqueT1P2UseCase,
    private val getNivelTanqueT2P2UseCase: GetNivelTanqueT2P2UseCase,
    ): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<LecturasPlantas>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<LecturasPlantas>>> = _uiState

    init {
        fetchNivelTanqueT1P1()
        fetchNivelTanqueT1P2()
        fetchNivelTanqueT2P2()
    }

    fun fetchNivelTanqueT1P1() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = getNivelTanqueT1P1UseCase()
            _uiState.value = when {
                result.isSuccess -> UiState.Success(result.getOrNull() ?: emptyList())
                result.isFailure -> UiState.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
                else ->  UiState.Error("Error desconocido")
            }
        }
    }

    fun fetchNivelTanqueT1P2() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = getNivelTanqueT1P2UseCase()
            _uiState.value = when {
                result.isSuccess -> UiState.Success(result.getOrNull() ?: emptyList())
                result.isFailure -> UiState.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
                else ->  UiState.Error("Error desconocido")
            }
        }
    }

    fun fetchNivelTanqueT2P2() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = getNivelTanqueT2P2UseCase()
            _uiState.value = when {
                result.isSuccess -> UiState.Success(result.getOrNull() ?: emptyList())
                result.isFailure -> UiState.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
                else ->  UiState.Error("Error desconocido")
            }
        }
    }
}

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}


