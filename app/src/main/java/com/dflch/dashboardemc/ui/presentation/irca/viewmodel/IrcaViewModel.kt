package com.dflch.dashboardemc.ui.presentation.irca.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dflch.dashboardemc.data.remote.dto.IrcaResponseDto
import com.dflch.dashboardemc.domain.model.irca.DataIrca
import com.dflch.dashboardemc.domain.usecases.irca.GetIrcaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class IrcaViewModel @Inject constructor(
    private val getIrcaUseCase: GetIrcaUseCase,
): ViewModel() {

    //private val _uiState = MutableStateFlow<UiState<DataIrca>>(UiState.Loading)
    //val uiState: StateFlow<UiState<DataIrca>> = _uiState

    private val _uiState = MutableStateFlow<UiState<List<DataIrca>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<DataIrca>>> = _uiState


    private val _status = MutableStateFlow("")
    val status: StateFlow<String> = _status

    init { fetchIrcaMonths()}

    /*
    fun fetchIrca(fechaIni: String, fechaFin: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val data = getIrcaUseCase(fechaIni, fechaFin)
                _uiState.value = when{
                    data.isSuccess -> UiState.Success(data.getOrThrow())
                    data.isFailure -> UiState.Error(data.exceptionOrNull()?.message ?: "Error desconocido")
                    else ->  UiState.Error("Error desconocido")
                }
                //_uiState.value = UiState.Success( data.getOrThrow() )
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchIrcaMonths() {
        val monthList = getLast12Months()

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val results = monthList.map { range ->
                    val response = getIrcaUseCase(range.startDate, range.endDate)
                    if (response.isSuccess) {
                        val data = response.getOrThrow()
                        // Retornar una nueva instancia con los campos adicionales
                        data.copy(
                            year = LocalDate.parse(range.startDate).year,
                            startDate = range.startDate,
                            endDate = range.endDate
                        )
                    } else {
                        null
                    }
                }.filterNotNull()

                _uiState.value = UiState.Success(results)

            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }



    /*fun refreshIrca(FechaIni: String, FechaFin: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val data = getIrcaUseCase(FechaIni, FechaFin)
                _uiState.value = UiState.Success( data.getOrThrow() )
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
    */
}

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

data class MonthRange(
    val year : String,
    val startDate: String,
    val endDate: String
)

@RequiresApi(Build.VERSION_CODES.O)
fun getLast12Months(): List<MonthRange> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val today = LocalDate.now()

    return (1 until 13).map { i ->
        val yearMonth = YearMonth.from(today).minusMonths(i.toLong())
        val start = yearMonth.atDay(1).format(formatter)
        val end = yearMonth.atEndOfMonth().format(formatter)
        MonthRange( yearMonth.toString(), start, end)
    }
}

