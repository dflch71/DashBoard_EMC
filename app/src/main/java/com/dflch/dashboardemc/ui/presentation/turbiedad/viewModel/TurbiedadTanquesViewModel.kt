package com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
import com.dflch.dashboardemc.domain.usecases.turbiedad.GetTurbiedadP1UseCase
import com.dflch.dashboardemc.domain.usecases.turbiedad.GetTurbiedadP2UseCase
import com.dflch.dashboardemc.domain.usecases.turbiedad.GetTurbiedadTanquesP1UseCase
import com.dflch.dashboardemc.domain.usecases.turbiedad.GetTurbiedadTanquesP2UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TurbiedadTanquesViewModel @Inject constructor(
    private val getTurbiedadTanquesP1UseCase: GetTurbiedadTanquesP1UseCase,
    private val getTurbiedadTanquesP2UseCase: GetTurbiedadTanquesP2UseCase,
): ViewModel() {


    private val _uiStateTanquesP1 = MutableStateFlow<UiStateTanquesP1<List<LecturasPlantas>>>(UiStateTanquesP1.Loading)
    val uiStateTanquesP1: StateFlow<UiStateTanquesP1<List<LecturasPlantas>>> = _uiStateTanquesP1

    private val _uiStateTanquesP2 = MutableStateFlow<UiStateTanquesP2<List<LecturasPlantas>>>(UiStateTanquesP2.Loading)
    val uiStateTanquesP2: StateFlow<UiStateTanquesP2<List<LecturasPlantas>>> = _uiStateTanquesP2

    init {
        fetchTurbiedadTanquesP1()
        fetchTurbiedadTanquesP2()
    }

    private fun fetchTurbiedadTanquesP1() {
        viewModelScope.launch {
            _uiStateTanquesP1.value = UiStateTanquesP1.Loading
            val result = getTurbiedadTanquesP1UseCase()
            _uiStateTanquesP1.value = when {
                result.isSuccess -> UiStateTanquesP1.Success(result.getOrNull() ?: emptyList())
                result.isFailure -> UiStateTanquesP1.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
                else ->  UiStateTanquesP1.Error("Error desconocido")
            }
        }
    }

    private fun fetchTurbiedadTanquesP2() {
        viewModelScope.launch {
            _uiStateTanquesP2.value = UiStateTanquesP2.Loading
            val result = getTurbiedadTanquesP2UseCase()
            _uiStateTanquesP2.value = when {
                result.isSuccess -> UiStateTanquesP2.Success(result.getOrNull() ?: emptyList())
                result.isFailure -> UiStateTanquesP2.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
                else ->  UiStateTanquesP2.Error("Error desconocido")
            }
        }
    }

    fun refreshDataTurbiedadTanquesP1() {
        viewModelScope.launch {
            _uiStateTanquesP1.value = UiStateTanquesP1.Loading
            try {
                val data = getTurbiedadTanquesP1UseCase()
                _uiStateTanquesP1.value = UiStateTanquesP1.Success(data.getOrNull() ?: emptyList())
            } catch (e: Exception) {
                _uiStateTanquesP1.value = UiStateTanquesP1.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun refreshDataTurbiedadTanquesP2() {
        viewModelScope.launch {
            _uiStateTanquesP2.value = UiStateTanquesP2.Loading
            try {
                val data = getTurbiedadTanquesP2UseCase()
                _uiStateTanquesP2.value = UiStateTanquesP2.Success(data.getOrNull() ?: emptyList())
            } catch (e: Exception) {
                _uiStateTanquesP2.value = UiStateTanquesP2.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

sealed class UiStateTanquesP1<out T> {
    object Loading : UiStateTanquesP1<Nothing>()
    data class Success<T>(val data: T) : UiStateTanquesP1<T>()
    data class Error(val message: String) : UiStateTanquesP1<Nothing>()
}

sealed class UiStateTanquesP2<out T> {
    object Loading : UiStateTanquesP2<Nothing>()
    data class Success<T>(val data: T) : UiStateTanquesP2<T>()
    data class Error(val message: String) : UiStateTanquesP2<Nothing>()
}


