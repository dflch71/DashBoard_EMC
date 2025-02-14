package com.dflch.dashboardemc.ui.presentation.turbiedad.view

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
import com.dflch.dashboardemc.ui.presentation.turbiedad.view.component.TurbiedadGrafMenu
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.TurbiedadTanquesViewModel
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.TurbiedadViewModel
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.UiStateP1
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.UiStateP2
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.UiStateTanquesP1
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.UiStateTanquesP2
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

// Data classes to represent the state
data class TurbiedadState(
    val data: List<LecturasPlantas> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class TurbiedadScreenState(
    val turbiedadPlant1: TurbiedadState = TurbiedadState(),
    val turbiedadPlant2: TurbiedadState = TurbiedadState(),
    val turbiedadTanquesPlant1: TurbiedadState = TurbiedadState(),
    val turbiedadTanquesPlant2: TurbiedadState = TurbiedadState(),
)


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TurbiedadScreen(
    turbiedadViewModel : TurbiedadViewModel,
    turbiedadTanquesViewModel : TurbiedadTanquesViewModel,
    navController: NavController
) {
    //Turbiedad Agua Cruda
    val uiStateP1 by turbiedadViewModel.uiStateP1.collectAsState()
    val uiStateP2 by turbiedadViewModel.uiStateP2.collectAsState()
    //Turbiedad Tanques
    val uiStateTanquesP1 by turbiedadTanquesViewModel.uiStateTanquesP1.collectAsState()
    val uiStateTanquesP2 by turbiedadTanquesViewModel.uiStateTanquesP2.collectAsState()

    var screenState by remember { mutableStateOf(TurbiedadScreenState()) }

    screenState = screenState.copy(
        turbiedadPlant1 = when (uiStateP1) {
            is UiStateP1.Loading -> TurbiedadState(isLoading = true)
            is UiStateP1.Success -> TurbiedadState(data = (uiStateP1 as UiStateP1.Success<List<LecturasPlantas>>).data)
            is UiStateP1.Error -> TurbiedadState(error = (uiStateP1 as UiStateP1.Error).message)
        },

        turbiedadPlant2 = when (uiStateP2) {
            is UiStateP2.Loading -> TurbiedadState(isLoading = true)
            is UiStateP2.Success -> TurbiedadState(data = (uiStateP2 as UiStateP2.Success<List<LecturasPlantas>>).data)
            is UiStateP2.Error -> TurbiedadState(error = (uiStateP2 as UiStateP2.Error).message)
        },

        turbiedadTanquesPlant1 = when (uiStateTanquesP1) {
            is UiStateTanquesP1.Loading -> TurbiedadState(isLoading = true)
            is UiStateTanquesP1.Success -> TurbiedadState(data = (uiStateTanquesP1 as UiStateTanquesP1.Success<List<LecturasPlantas>>).data)
            is UiStateTanquesP1.Error -> TurbiedadState(error = (uiStateTanquesP1 as UiStateTanquesP1.Error).message)
        },

        turbiedadTanquesPlant2 = when (uiStateTanquesP2) {
            is UiStateTanquesP2.Loading -> TurbiedadState(isLoading = true)
            is UiStateTanquesP2.Success -> TurbiedadState(data = (uiStateTanquesP2 as UiStateTanquesP2.Success<List<LecturasPlantas>>).data)
            is UiStateTanquesP2.Error -> TurbiedadState(error = (uiStateTanquesP2 as UiStateTanquesP2.Error).message)
        }
    )

    // Actualiza los datos cada 60 segundos
    LaunchedEffect(Unit) {
        while (isActive) {
            turbiedadViewModel.refreshDataTurbiedadP1()
            turbiedadViewModel.refreshDataTurbiedadP2()
            turbiedadTanquesViewModel.refreshDataTurbiedadTanquesP1()
            turbiedadTanquesViewModel.refreshDataTurbiedadTanquesP2()
            delay(100000L) // Actualiza cada 120 segundos
        }
    }

    // Display the UI based on the screen state
    if (screenState.turbiedadPlant1.error != null || screenState.turbiedadPlant2.error != null ||
        screenState.turbiedadTanquesPlant1 .error != null || screenState.turbiedadTanquesPlant2.error != null) {
        val errorMessage = screenState.turbiedadPlant1.error ?: screenState.turbiedadPlant1.error ?:
                                  screenState.turbiedadTanquesPlant1.error ?:  screenState.turbiedadTanquesPlant2.error ?: ""
        ErrorScreen(errorMessage)
    } else {
        if (!screenState.turbiedadPlant1.isLoading && !screenState.turbiedadPlant2.isLoading &&
            !screenState.turbiedadTanquesPlant1.isLoading && !screenState.turbiedadTanquesPlant2.isLoading) {

            TurbiedadPlantas(
                screenState.turbiedadPlant1.data,
                screenState.turbiedadPlant2.data,
                screenState.turbiedadTanquesPlant1.data,
                screenState.turbiedadTanquesPlant2.data,
                onClickP1 = { navController.navigate("turbiedad_planta01_screen") },
                onClickP2 = { navController.navigate("turbiedad_planta02_screen") }
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
fun TurbiedadPlantas(
    turbiedadP1: List<LecturasPlantas>,
    turbiedadP2: List<LecturasPlantas>,
    turbiedadTanquesP1: List<LecturasPlantas>,
    turbiedadTanquesP2: List<LecturasPlantas>,
    onClickP1 : () -> Unit = {},
    onClickP2 : () -> Unit = {},
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TurbiedadCard(
            title = "Turbiedad Planta 01 (UNT)",
            dataAguaCruda = turbiedadP1,
            dataTanques = turbiedadTanquesP1,
            onClick = onClickP1,
            modifier = Modifier.weight(0.34f)
        )
        TurbiedadCard(
            title = "Turbiedad Planta 02 (UNT)",
            dataAguaCruda = turbiedadP2,
            dataTanques = turbiedadTanquesP2,
            onClick = onClickP2,
            modifier = Modifier.weight(0.33f)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TurbiedadCard(
    title: String,
    dataAguaCruda: List<LecturasPlantas>,
    dataTanques: List<LecturasPlantas>,
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
                var fechaDesde = "";
                var fechaHasta = "";
                var turbiedad = "";
                var fechaDesdeTanque = "";
                var turbiedadTanque = "";

                Titulo(title)
                if (dataAguaCruda.isNotEmpty()) {
                    fechaDesde = dataAguaCruda[0].fecha ?: ""
                    fechaHasta = dataAguaCruda.last().fecha ?: ""
                    turbiedad = dataAguaCruda[0].lectura.toString() ?: ""
                    TituloFecha(fechaDesde, fechaHasta)

                    if (dataTanques.isNotEmpty()) {
                        fechaDesdeTanque = dataTanques[0].fecha ?: ""
                        turbiedadTanque = dataTanques[0].lectura.toString() ?: ""
                    }

                    TurbiedadData(fechaDesde, turbiedad, fechaDesdeTanque, turbiedadTanque, dataAguaCruda)

                } else { Titulo("No hay datos para mostrar") }

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TurbiedadData(
    fechaDesdeAguaCruda: String,
    turbiedadAguaCruda: String,
    fechaDesdeTanques: String,
    turbiedadTanques: String,
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
                    SubTitulo("Agua Cruda")
                    DatePart(fechaDesdeAguaCruda)
                    Text(
                        text = turbiedadAguaCruda,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Clip
                    )
                    val difDate = calcularDiferenciaTiempo(fechaDesdeAguaCruda.replace(".", "")) ?: ""
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

            ElevatedCard(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {
                Column {
                    SubTitulo("Tanques Planta")
                    DatePart(fechaDesdeAguaCruda)
                    Text(
                        text = turbiedadTanques,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Clip
                    )

                    val difDate = calcularDiferenciaTiempo(fechaDesdeTanques.replace(".", "")) ?: ""
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
            TurbiedadGrafMenu(turbiedadList)
        }
    }
}


@Composable
fun TurbiedadPlantas_(
    turbiedadP1: List<LecturasPlantas>,
    turbiedadP2: List<LecturasPlantas>,
    turbiedadTanquesP1: List<LecturasPlantas>,
    turbiedadTanquesP2: List<LecturasPlantas>,
    onClickP1 : () -> Unit = {},
    onClickP2 : () -> Unit = {},
){

    // Actualiza los datos iniciales
    var p1FechaDesde : String by remember { mutableStateOf("")}
    var p1FechaHasta : String by remember { mutableStateOf("")}
    var p2FechaDesde : String by remember { mutableStateOf("")}
    var p2FechaHasta : String by remember { mutableStateOf("")}

    var p1AguaCruda : String by remember { mutableStateOf("")}
    var p2AguaCruda : String by remember { mutableStateOf("")}

    //Valores Turbiedad Tanques
    var p1TanquesFP1 : String by remember { mutableStateOf("")}
    var p2TanquesFP2 : String by remember { mutableStateOf("")}
    var p1Tanques    : String by remember { mutableStateOf("")}
    var p2Tanques    : String by remember { mutableStateOf("")}

    var isDataP1 : Boolean = remember { !turbiedadP1.isNullOrEmpty() }
    var isDataP2 : Boolean = remember { !turbiedadP2.isNullOrEmpty() }
    var isDataTanquesP1 : Boolean = remember { !turbiedadTanquesP1.isNullOrEmpty() }
    var isDataTanquesP2 : Boolean = remember { !turbiedadTanquesP2.isNullOrEmpty() }


    if (isDataP1) {
        p1FechaDesde = turbiedadP1[0].fecha
        p1FechaHasta = turbiedadP1[turbiedadP1.size - 1].fecha
        p1AguaCruda  = turbiedadP1[0].lectura.toString()
    }

    if (isDataP2) {
        p2FechaDesde = turbiedadP2[0].fecha
        p2FechaHasta = turbiedadP2[turbiedadP2.size - 1].fecha
        p2AguaCruda  = turbiedadP2[0].lectura.toString()
    }

    if (isDataTanquesP1) {
        p1TanquesFP1 = turbiedadTanquesP1[0].fecha
        p1Tanques    = turbiedadTanquesP1[0].lectura.toString()
    }

    if (isDataTanquesP2) {
        p2TanquesFP2 = turbiedadTanquesP2[0].fecha
        p2Tanques    = turbiedadTanquesP2[0].lectura.toString()
    }

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
                    Titulo("Turbiedad Planta 01 (UNT)")
                    if (isDataP1) {
                        TituloFecha(p1FechaDesde, p1FechaHasta)
                        valoresTurbiedad(p1FechaDesde, p1AguaCruda, p1TanquesFP1, p1Tanques, turbiedadP1)
                    } else {
                        Titulo("No hay datos para mostrar Planta 01")
                    }
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
                    Titulo("Turbiedad Planta 02 (UNT)")
                    if (isDataP2) {
                        TituloFecha(p2FechaDesde, p2FechaHasta)
                        valoresTurbiedad(p2FechaDesde, p2AguaCruda, p2TanquesFP2 ,p2Tanques, turbiedadP2)
                    } else {
                        Titulo("No hay datos para mostrar Planta 02")
                    }
                }
            }
        }
    }
}

@Composable
private fun valoresTurbiedad(
    FechaDesde: String,
    AguaCruda: String,
    FechaTanques: String,
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
                    SubTitulo("Agua Cruda")
                    DatePart(FechaDesde)
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
                    SubTitulo("Tanques Almacenamiento")
                    DatePart(FechaTanques)
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
