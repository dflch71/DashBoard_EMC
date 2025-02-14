package com.dflch.dashboardemc.ui.presentation.niveltanque.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
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

    private val _uiStateT1P1 = MutableStateFlow<UiStateT1P1<List<LecturasPlantas>>>(UiStateT1P1.Loading)
    val uiStateT1P1: StateFlow<UiStateT1P1<List<LecturasPlantas>>> = _uiStateT1P1

    private val _uiStateT1P2 = MutableStateFlow<UiStateT1P2<List<LecturasPlantas>>>(UiStateT1P2.Loading)
    val uiStateT1P2: StateFlow<UiStateT1P2<List<LecturasPlantas>>> = _uiStateT1P2

    private val _uiStateT2P2 = MutableStateFlow<UiStateT2P2<List<LecturasPlantas>>>(UiStateT2P2.Loading)
    val uiStateT2P2: StateFlow<UiStateT2P2<List<LecturasPlantas>>> = _uiStateT2P2

    init {
        fetchNivelTanqueT1P1()
        fetchNivelTanqueT1P2()
        fetchNivelTanqueT2P2()
    }

    fun fetchNivelTanqueT1P1() {
        viewModelScope.launch {
            _uiStateT1P1.value = UiStateT1P1.Loading
            val result = getNivelTanqueT1P1UseCase()
            _uiStateT1P1.value = when {
                result.isSuccess -> UiStateT1P1.Success(result.getOrNull() ?: emptyList())
                result.isFailure -> UiStateT1P1.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
                else ->  UiStateT1P1.Error("Error desconocido")
            }
        }
    }

    fun fetchNivelTanqueT1P2() {
        viewModelScope.launch {
            _uiStateT1P2.value = UiStateT1P2.Loading
            val result = getNivelTanqueT1P2UseCase()
            _uiStateT1P2.value = when {
                result.isSuccess -> UiStateT1P2.Success(result.getOrNull() ?: emptyList())
                result.isFailure -> UiStateT1P2.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
                else ->  UiStateT1P2.Error("Error desconocido")
            }
        }
    }

    fun fetchNivelTanqueT2P2() {
        viewModelScope.launch {
            _uiStateT2P2.value = UiStateT2P2.Loading
            val result = getNivelTanqueT2P2UseCase()
            _uiStateT2P2.value = when {
                result.isSuccess -> UiStateT2P2.Success(result.getOrNull() ?: emptyList())
                result.isFailure -> UiStateT2P2.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
                else ->  UiStateT2P2.Error("Error desconocido")
            }
        }
    }
}

sealed class UiStateT1P1<out T> {
    object Loading : UiStateT1P1<Nothing>()
    data class Success<T>(val data: T) : UiStateT1P1<T>()
    data class Error(val message: String) : UiStateT1P1<Nothing>()
}

sealed class UiStateT1P2<out T> {
    object Loading : UiStateT1P2<Nothing>()
    data class Success<T>(val data: T) : UiStateT1P2<T>()
    data class Error(val message: String) : UiStateT1P2<Nothing>()
}

sealed class UiStateT2P2<out T> {
    object Loading : UiStateT2P2<Nothing>()
    data class Success<T>(val data: T) : UiStateT2P2<T>()
    data class Error(val message: String) : UiStateT2P2<Nothing>()
}


