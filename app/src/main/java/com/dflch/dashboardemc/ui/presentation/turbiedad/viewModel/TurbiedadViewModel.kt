package com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
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


    private val _uiStateP1 = MutableStateFlow<UiStateP1<List<LecturasPlantas>>>(UiStateP1.Loading)
    val uiStateP1: StateFlow<UiStateP1<List<LecturasPlantas>>> = _uiStateP1

    private val _uiStateP2 = MutableStateFlow<UiStateP2<List<LecturasPlantas>>>(UiStateP2.Loading)
    val uiStateP2: StateFlow<UiStateP2<List<LecturasPlantas>>> = _uiStateP2

    init {
        fetchTurbiedadP1()
        fetchTurbiedadP2()
    }

    private fun fetchTurbiedadP1() {
        viewModelScope.launch {
            _uiStateP1.value = UiStateP1.Loading
            val result = getTurbiedadP1UseCase()
            _uiStateP1.value = when {
                result.isSuccess -> UiStateP1.Success(result.getOrNull() ?: emptyList())
                result.isFailure -> UiStateP1.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
                else ->  UiStateP1.Error("Error desconocido")
            }
        }
    }

    private fun fetchTurbiedadP2() {
        viewModelScope.launch {
            _uiStateP2.value = UiStateP2.Loading
            val result = getTurbiedadP2UseCase()
            _uiStateP2.value = when {
                result.isSuccess -> UiStateP2.Success(result.getOrNull() ?: emptyList())
                result.isFailure -> UiStateP2.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
                else ->  UiStateP2.Error("Error desconocido")
            }
        }
    }

    fun refreshDataTurbiedadP1() {
        viewModelScope.launch {
            _uiStateP1.value = UiStateP1.Loading
            try {
                val data = getTurbiedadP1UseCase()
                _uiStateP1.value = UiStateP1.Success(data.getOrNull() ?: emptyList())
            } catch (e: Exception) {
                _uiStateP1.value = UiStateP1.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun refreshDataTurbiedadP2() {
        viewModelScope.launch {
            _uiStateP2.value = UiStateP2.Loading
            try {
                val data = getTurbiedadP2UseCase()
                _uiStateP2.value = UiStateP2.Success(data.getOrNull() ?: emptyList())
            } catch (e: Exception) {
                _uiStateP2.value = UiStateP2.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

sealed class UiStateP1<out T> {
    object Loading : UiStateP1<Nothing>()
    data class Success<T>(val data: T) : UiStateP1<T>()
    data class Error(val message: String) : UiStateP1<Nothing>()
}

sealed class UiStateP2<out T> {
    object Loading : UiStateP2<Nothing>()
    data class Success<T>(val data: T) : UiStateP2<T>()
    data class Error(val message: String) : UiStateP2<Nothing>()
}


