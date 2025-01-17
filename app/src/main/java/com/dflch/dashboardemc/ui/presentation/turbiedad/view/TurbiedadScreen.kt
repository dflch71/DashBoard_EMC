package com.dflch.dashboardemc.ui.presentation.turbiedad.view

import android.icu.lang.UCharacter.toUpperCase
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dflch.dashboardemc.core.utils.Utility.Companion.extractDateTimeParts
import com.dflch.dashboardemc.domain.model.nivelrio.LecturasPlantas
import com.dflch.dashboardemc.ui.presentation.nivelRio.view.component.AlertDialogNivelRio
import com.dflch.dashboardemc.ui.presentation.nivelRio.viewmodel.UiState
import com.dflch.dashboardemc.ui.presentation.turbiedad.view.component.TurbiedadGrafMenu
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.TurbiedadViewModel
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.UiStateP1
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.UiStateP2
import kotlinx.coroutines.delay

@Composable
fun TurbiedadScreen(
    turbiedadViewModel : TurbiedadViewModel,
    navController: NavController
) {
    val uiStateP1 by turbiedadViewModel.uiStateP1.collectAsState()
    val uiStateP2 by turbiedadViewModel.uiStateP2.collectAsState()

    var turbiedadP1: List<LecturasPlantas> by remember { mutableStateOf(emptyList()) }
    var turbiedadP2: List<LecturasPlantas> by remember { mutableStateOf(emptyList()) }

    var isErrorMsg by remember { mutableStateOf(false) }
    var msgError by remember { mutableStateOf("") }

    // Actualiza los datos cada 60 segundos
    LaunchedEffect(Unit) {
        while (true) {
            turbiedadViewModel.refreshDataTurbiedadP1()
            turbiedadViewModel.refreshDataTurbiedadP2()
            delay(900000L) // Actualiza cada 120 segundos
        }
    }

    when (uiStateP1) {
        is UiStateP1.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiStateP1.Success -> {
            turbiedadP1 = (uiStateP1 as UiStateP1.Success<List<LecturasPlantas>>).data
        }

        is UiStateP1.Error -> {
            //val errorMessage = (uiStateP1 as UiStateP1.Error).message
            //Text(text = "Error: $errorMessage", color = Color.Red)
            msgError = (uiStateP1 as UiState.Error).message
            isErrorMsg = true
        }
    }

    when (uiStateP2) {
        is UiStateP2.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiStateP2.Success -> {
            turbiedadP2 = (uiStateP2 as UiStateP2.Success<List<LecturasPlantas>>).data
        }

        is UiStateP2.Error -> {
            //val errorMessage = (uiStateP2 as UiStateP2.Error).message
            //Text(text = "Error: $errorMessage", color = Color.Red)
            msgError = (uiStateP2 as UiState.Error).message
            isErrorMsg = true
        }

    }

    if (isErrorMsg) {
        Text(
            text = msgError,
            color = Color.Red,
            modifier = Modifier.padding(14.dp),
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center
        )
        return
    }

    if (turbiedadP1.isNullOrEmpty()) {
        turbiedadP1 = emptyList()
        Text(text = "No hay datos disponibles para la planta 1", color = Color.Red)
    }

    if (turbiedadP2.isNullOrEmpty()) {
        turbiedadP2 = emptyList()
        Text(text = "No hay datos disponibles para la planta 2", color = Color.Red)
    }


    TurbiedadPlantas(
        turbiedadP1,
        turbiedadP2,
        onClickP1 = { navController.navigate("turbiedad_planta01_screen") },
        onClickP2 = { navController.navigate("turbiedad_planta02_screen") }
    )
}

@Composable
fun TurbiedadPlantas(
    turbiedadP1: List<LecturasPlantas>,
    turbiedadP2: List<LecturasPlantas>,
    onClickP1 : () -> Unit = {},
    onClickP2 : () -> Unit = {},
){

    // Actualiza los datos iniciales
    var p1FechaDesde : String by remember { mutableStateOf("")}
    var p1FechaHasta : String by remember { mutableStateOf("")}
    var p2FechaDesde : String by remember { mutableStateOf("")}
    var p2FechaHasta : String by remember { mutableStateOf("")}

    var p1AguaCruda : String by remember { mutableStateOf("")}
    var p1Tanques   : String by remember { mutableStateOf("")}
    var p2AguaCruda : String by remember { mutableStateOf("")}
    var p2Tanques   : String by remember { mutableStateOf("")}

    p1FechaDesde = turbiedadP1[0].fecha
    p1FechaHasta = turbiedadP1[turbiedadP1.size - 1].fecha
    p1AguaCruda  = turbiedadP1[0].lectura.toString()
    p1Tanques    = "0.0X" //Pendiente

    p2FechaDesde = turbiedadP2[0].fecha
    p2FechaHasta = turbiedadP2[turbiedadP2.size - 1].fecha
    p2AguaCruda  = turbiedadP2[0].lectura.toString()
    p2Tanques    = "0.0X" //Pendiente

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxSize()
        ) {
            OutlinedCard(
                modifier = Modifier
                    .clickable{ onClickP1() }
                    .fillMaxSize()
                    .padding(2.dp),

                shape = RoundedCornerShape(15.dp),
            ) {
                Column {
                    titulo("Turbiedad Planta 01 (UNT)")
                    tituloFecha(p1FechaDesde, p1FechaHasta)
                    valoresTurbiedad(p1FechaDesde, p1AguaCruda, p1Tanques, turbiedadP1)
                }
            }
        }

        Box(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxSize()
        ) {
            OutlinedCard(
                modifier = Modifier
                    .clickable{  onClickP2() }
                    .fillMaxSize()
                    .padding(2.dp),
                shape = RoundedCornerShape(15.dp),
            ) {
                Column {
                    titulo("Turbiedad Planta 02 (UNT)")
                    tituloFecha(p2FechaDesde, p2FechaHasta)
                    valoresTurbiedad(p2FechaDesde, p2AguaCruda, p2Tanques, turbiedadP2)
                }
            }
        }
    }
}

@Composable
private fun valoresTurbiedad(
    FechaDesde: String,
    AguaCruda: String,
    Tanques: String,
    turbiedadList: List<LecturasPlantas>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Column(
            modifier = Modifier.weight(0.4f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ElevatedCard(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {

                Column {
                    subTitulo("Agua Cruda")
                    datePart(FechaDesde)
                    Text(
                        text = AguaCruda,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        softWrap = false, // Evita que el texto se envuelva en varias líneas
                        overflow = TextOverflow.Clip // No muestra "..." si no cabe
                    )
                }
            }

            ElevatedCard(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {
                Column {
                    subTitulo("Tanques Almacenamiento")
                    datePart(FechaDesde)
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp, end = 4.dp),
                        text = Tanques,
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        softWrap = false, // Evita que el texto se envuelva en varias líneas
                        overflow = TextOverflow.Clip // No muestra "..." si no cabe
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .weight(0.6f)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            TurbiedadGrafMenu(turbiedadList)
        }
    }
}

@Composable
private fun titulo(muestra: String) {
    Text(
        text = muestra,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        style = MaterialTheme.typography.bodyLarge,
        color = Color.Black,
        textAlign = TextAlign.Center,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Clip
    )
}

@Composable
private fun tituloFecha(fecha01: String, fecha02: String)  {
    Text(
        text = toUpperCase("${fecha01} - ${fecha02}"),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.labelMedium,
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun subTitulo(Lugar: String)  {
    Text(
        text = Lugar,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.labelMedium,
        color = Color.Black,
        textAlign = TextAlign.Center,
        maxLines = 2,
        overflow = TextOverflow.Clip,
    )
}

@Composable
private fun datePart(fecha: String){
    val (datePart, timePart) = extractDateTimeParts(fecha) // Or extractDateTimePartsRegex(text)
    val fechaHora = "$datePart $timePart"
    Text(
        text = fechaHora,
        maxLines = 1,
        fontSize = 10.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}






