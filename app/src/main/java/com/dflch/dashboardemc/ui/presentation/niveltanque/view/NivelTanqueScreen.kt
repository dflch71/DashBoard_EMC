package com.dflch.dashboardemc.ui.presentation.niveltanque.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.navigation.NavController
import com.dflch.dashboardemc.core.utils.Utility.Companion.calcularDiferenciaTiempo
import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
import com.dflch.dashboardemc.ui.components.DatePart
import com.dflch.dashboardemc.ui.components.SubTitulo
import com.dflch.dashboardemc.ui.components.Titulo
import com.dflch.dashboardemc.ui.components.TituloFecha
import com.dflch.dashboardemc.ui.presentation.niveltanque.viewModel.NivelTanqueViewModel
import com.dflch.dashboardemc.ui.presentation.niveltanque.viewModel.UiStateT1P1
import com.dflch.dashboardemc.ui.presentation.niveltanque.viewModel.UiStateT1P2
import com.dflch.dashboardemc.ui.presentation.niveltanque.viewModel.UiStateT2P2
import com.dflch.dashboardemc.ui.presentation.turbiedad.view.component.TurbiedadGrafMenu
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

// Data classes to represent the state
data class TankState(
    val data: List<LecturasPlantas> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class NivelTanqueScreenState(
    val tank1Plant1: TankState = TankState(),
    val tank1Plant2: TankState = TankState(),
    val tank2Plant2: TankState = TankState()
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NivelTanqueScreen(
    nivelTanqueViewModel: NivelTanqueViewModel,
    navController: NavController
) {
    // Collect all UI states into a single state object
    val uiStateT1P1 by nivelTanqueViewModel.uiStateT1P1.collectAsState()
    val uiStateT1P2 by nivelTanqueViewModel.uiStateT1P2.collectAsState()
    val uiStateT2P2 by nivelTanqueViewModel.uiStateT2P2.collectAsState()

    var screenState by remember { mutableStateOf(NivelTanqueScreenState()) }

    // Update the screen state based on the UI states
    screenState = screenState.copy(
        tank1Plant1 = when (uiStateT1P1) {
            is UiStateT1P1.Loading -> TankState(isLoading = true)
            is UiStateT1P1.Success -> TankState(data = (uiStateT1P1 as UiStateT1P1.Success<List<LecturasPlantas>>).data)
            is UiStateT1P1.Error -> TankState(error = (uiStateT1P1 as UiStateT1P1.Error).message)
        },
        tank1Plant2 = when (uiStateT1P2) {
            is UiStateT1P2.Loading -> TankState(isLoading = true)
            is UiStateT1P2.Success -> TankState(data = (uiStateT1P2 as UiStateT1P2.Success<List<LecturasPlantas>>).data)
            is UiStateT1P2.Error -> TankState(error = (uiStateT1P2 as UiStateT1P2.Error).message)
        },
        tank2Plant2 = when (uiStateT2P2) {
            is UiStateT2P2.Loading -> TankState(isLoading = true)
            is UiStateT2P2.Success -> TankState(data = (uiStateT2P2 as UiStateT2P2.Success<List<LecturasPlantas>>).data)
            is UiStateT2P2.Error -> TankState(error = (uiStateT2P2 as UiStateT2P2.Error).message)
        }
    )

    // Fetch data periodically
    LaunchedEffect(Unit) {
        while (isActive) {
            nivelTanqueViewModel.fetchNivelTanqueT1P1()
            nivelTanqueViewModel.fetchNivelTanqueT1P2()
            nivelTanqueViewModel.fetchNivelTanqueT2P2()
            delay(300000L) // Update every 300 seconds
        }
    }

    // Display the UI based on the screen state
    if (screenState.tank1Plant1.error != null || screenState.tank1Plant2.error != null || screenState.tank2Plant2.error != null) {
        val errorMessage = screenState.tank1Plant1.error ?: screenState.tank1Plant2.error ?: screenState.tank2Plant2.error ?: ""
        ErrorScreen(errorMessage)
    } else {
        if (!screenState.tank1Plant1.isLoading && !screenState.tank1Plant2.isLoading && !screenState.tank2Plant2.isLoading) {
            NivelTanques(
                tank1Plant1Data = screenState.tank1Plant1.data,
                tank1Plant2Data = screenState.tank1Plant2.data,
                tank2Plant2Data = screenState.tank2Plant2.data,
                onClickT1P1 = { navController.navigate("nivel_tanque_T1P1_screen") },
                onClickT1P2 = { navController.navigate("nivel_tanque_T1P2_screen") },
                onClickT2P2 = { navController.navigate("nivel_tanque_T2P2_screen") }
            )
        } else {
            LoadingScreen()
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(errorMessage: String) {
    Text(
        text = errorMessage,
        color = Color.Red,
        modifier = Modifier.padding(14.dp),
        style = MaterialTheme.typography.labelLarge,
        textAlign = TextAlign.Center
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NivelTanques(
    tank1Plant1Data: List<LecturasPlantas>,
    tank1Plant2Data: List<LecturasPlantas>,
    tank2Plant2Data: List<LecturasPlantas>,
    onClickT1P1: () -> Unit,
    onClickT1P2: () -> Unit,
    onClickT2P2: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TankCard(
            title = "Nivel Tanque 01 Planta 01 (Mts)",
            data = tank1Plant1Data,
            onClick = onClickT1P1,
            modifier = Modifier.weight(0.34f)
        )
        TankCard(
            title = "Nivel Tanque 01 Planta 02 (Mts)",
            data = tank1Plant2Data,
            onClick = onClickT1P2,
            modifier = Modifier.weight(0.33f)
        )
        TankCard(
            title = "Nivel Tanque 02 Planta 02 (Mts)",
            data = tank2Plant2Data,
            onClick = onClickT2P2,
            modifier = Modifier.weight(0.33f)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TankCard(
    title: String,
    data: List<LecturasPlantas>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        OutlinedCard(
            modifier = Modifier
                .clickable { onClick() }
                .fillMaxSize()
                .padding(2.dp),
            shape = RoundedCornerShape(15.dp),
        ) {
            Column {
                Titulo(title)
                if (data.isNotEmpty()) {
                    val fechaDesde = data[0].fecha ?: ""
                    val fechaHasta = data.last().fecha ?: ""
                    val nivelTanque = data[0].lectura.toString()
                    TituloFecha(fechaDesde, fechaHasta)
                    TankData(fechaDesde, nivelTanque, data)
                } else {
                    Titulo("No hay datos para mostrar")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TankData(
    fechaDesde: String,
    nivelTanque: String,
    nivelTanqueList: List<LecturasPlantas>
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
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {
                Column {
                    SubTitulo("Nivel Tanque")
                    DatePart(fechaDesde)
                    Text(
                        text = nivelTanque,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Clip
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val difDate = calcularDiferenciaTiempo(fechaDesde.replace(".", "")) ?: ""
                    Text(
                        text = difDate,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.labelMedium,
                        color = if (difDate.contains("horas")) Color.Red else Color.Gray,
                        textAlign = TextAlign.Center,
                        minLines = 1,
                        overflow = TextOverflow.Clip,
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
            TurbiedadGrafMenu(nivelTanqueList)
        }
    }
}
