package com.dflch.dashboardemc.ui.presentation.niveltanque.view

import android.icu.lang.UCharacter.toUpperCase
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.dflch.dashboardemc.R
import com.dflch.dashboardemc.core.utils.Utility.Companion.extractDateTimeParts
import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
import com.dflch.dashboardemc.ui.components.CharType
import com.dflch.dashboardemc.ui.presentation.niveltanque.viewModel.NivelTanqueViewModel
import com.dflch.dashboardemc.ui.presentation.niveltanque.viewModel.UiStateT1P1
import com.dflch.dashboardemc.ui.presentation.niveltanque.viewModel.UiStateT2P2
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.PopupProperties
import ir.ehsannarmani.compose_charts.models.StrokeStyle
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NivelTanqueT2P2Screen(
    nivelTanqueViewModel: NivelTanqueViewModel,
    navHost: NavHostController
){
    val uiState by nivelTanqueViewModel.uiStateT2P2.collectAsState()

    // Actualiza los datos cada 60 segundos
    LaunchedEffect(Unit) {
        while (true) {
            nivelTanqueViewModel.fetchNivelTanqueT2P2()
            delay(300000L) // Actualiza cada 60 segundos
        }
    }

    var selectedButton by remember { mutableStateOf("Línea") }
    var selectedChart by remember { mutableStateOf<CharType?>(CharType.Line) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Nivel Tanque Plantas (Mts)",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navHost.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {},
                scrollBehavior = scrollBehavior
            )
        },

        bottomBar = {
            BottomAppBar(actions = {

                Spacer(modifier = Modifier.width(12.dp))


                IconButton(onClick = {
                    selectedButton = "Línea"
                    selectedChart = CharType.Line
                }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_line_chart),
                        contentDescription = "Build description"
                    )
                }
            })
        }

    ){ innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            when (uiState) {
                is UiStateT2P2.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is UiStateT2P2.Success -> {
                    val NivelTanqueT2P2 = (uiState as UiStateT2P2.Success<List<LecturasPlantas>>).data

                    Box(modifier = Modifier.fillMaxSize()) {
                        if (NivelTanqueT2P2.isNotEmpty()) {
                            Column {
                                Box(
                                    modifier = Modifier.weight(0.12f)
                                ) {
                                    Column {
                                        Text(
                                            text = "Nivel Tanque Planta 2 - T2",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(2.dp),
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = Color.Black,
                                            textAlign = TextAlign.Center
                                        )

                                        Text(
                                            text = toUpperCase("${NivelTanqueT2P2[0].fecha} - ${NivelTanqueT2P2[NivelTanqueT2P2.size - 1].fecha}"),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(2.dp),
                                            style = MaterialTheme.typography.labelMedium,
                                            color = Color.Black,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }



                                Box(
                                    modifier = Modifier
                                        .weight(0.20f)
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(4.dp)
                                    ) {
                                        val NivelTanqueT1P1Unicos = NivelTanqueT2P2.distinctBy { it.fecha }
                                        NivelTanqueT2P2Content(NivelTanqueT1P1Unicos)
                                    }
                                }

                                Box(modifier = Modifier.weight(1.0f)) {
                                    NivelTanqueT2P2Graf(NivelTanqueT2P2)
                                }
                            }
                        } else {
                            AlertDialogNivelTanque2(nivelTanqueViewModel)
                        }

                    }
                }
                is UiStateT2P2.Error -> {
                    val errorMessage = (uiState as UiStateT1P1.Error).message
                    Text(text = "Error: $errorMessage", color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun AlertDialogNivelTanque2(viewModel: NivelTanqueViewModel) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Error Cargando Información")
            },
            text = {
                Text(
                    "- No hay datos disponibles \n" +
                            "- Verifar la conexión a internet"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.fetchNivelTanqueT1P1()
                        openDialog.value = false
                    }
                ) {
                    Text("Aceptar")
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Salir")
                }
            },

            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        )
    }
}

@Composable
fun NivelTanqueT2P2Content(nivelTanqueT2P2: List<LecturasPlantas>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(nivelTanqueT2P2) { nivelTanqueT1P2 ->
            NivelTanqueT2P2Item(nivelTanqueT1P2)
        }
    }
}

@Composable
fun NivelTanqueT2P2Item(nivelTanqueT2P2: LecturasPlantas) {
    Column(
        modifier = Modifier
            .width(90.dp)
            .wrapContentHeight()
            .background(
                color = if (nivelTanqueT2P2.lectura <= 0.5) Color(0xFFFFA500) else colorResource(id = R.color.purple_500),
                shape = RoundedCornerShape(15.dp)
            )
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = nivelTanqueT2P2.lectura.toString(),
            maxLines = 1,
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        val (datePart, timePart) = extractDateTimeParts(nivelTanqueT2P2.fecha) // Or extractDateTimePartsRegex(text)
        Text(
            text = toUpperCase(datePart?.toString() ?: "Fecha"),
            maxLines = 2,
            fontSize = 10.sp,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = timePart?.toString() ?: "Hora",
            maxLines = 1,
            fontSize = 10.sp,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NivelTanqueT2P2Graf(nivelTanqueT2P2: List<LecturasPlantas>) {
    var horas = mutableListOf<String>()
    var nivelTanqueLectura = mutableListOf<Double>()
    var horasLectura = 0
    var lastHour = 0

    for (nivelTanque in nivelTanqueT2P2)  {
        horasLectura++
        if (horas.size >= 12) break

        if (horasLectura < 30) {
            if (horasLectura == 1) {
                lastHour = nivelTanque.hora
                horas.add(nivelTanque.hora.toString())
                nivelTanqueLectura.add(nivelTanque.lectura)
            } else {
                if (lastHour != nivelTanque.hora) {
                    lastHour = nivelTanque.hora
                    horas.add(nivelTanque.hora.toString())
                    nivelTanqueLectura.add(nivelTanque.lectura)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .padding(top = 4.dp, bottom = 8.dp)
            .fillMaxSize()
    ) {
        LineChart(
            labelProperties = LabelProperties(
                enabled = true,
                labels = horas.reversed()
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp),
            data = remember {
                listOf(
                    Line(
                        label = "Nivel Tanque 2 Planta 2",
                        values = nivelTanqueLectura.reversed(),
                        color = SolidColor(Color(0xFF2596be)),
                        firstGradientFillColor = Color(0xFF2596be).copy(alpha = .5f),
                        secondGradientFillColor = Color.Transparent,
                        strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                        gradientAnimationDelay = 1000,
                        drawStyle = DrawStyle.Stroke(width = 2.dp),
                        dotProperties = DotProperties(
                            enabled = true,
                            color = SolidColor(Color.White),
                            strokeWidth = 2.dp,
                            radius = 4.dp,
                            strokeColor = SolidColor(Color.LightGray),
                        )
                    ),

                    Line(
                        label = "Alerta (0.5 Mts)", //909.50
                        values = listOf(0.5, 0.5, 0.5, 0.5),
                        color =  SolidColor(Color(0xFFFFA500)),
                        firstGradientFillColor = Color(0xFFFFCC80).copy(alpha = .7f),
                        secondGradientFillColor = Color.Transparent,
                        strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                        gradientAnimationDelay = 1000,
                        drawStyle = DrawStyle.Stroke(
                            width = 3.dp,
                            strokeStyle = StrokeStyle.Dashed(intervals = floatArrayOf(10f, 10f), phase = 15f)
                        )
                    ),
                )
            },

            animationMode = AnimationMode.Together(delayBuilder = { it * 500L }),
            popupProperties = PopupProperties(
                enabled = true,
                animationSpec = tween(300),
                duration = 2000L,
                textStyle = MaterialTheme.typography.labelSmall,
                containerColor = Color.White,
                cornerRadius = 8.dp,
                contentHorizontalPadding = 4.dp,
                contentVerticalPadding = 2.dp,
                contentBuilder = { value-> "%.2f".format(value)+" Mts" }
            ),

            gridProperties = GridProperties(
                xAxisProperties = GridProperties.AxisProperties(
                    enabled = true,
                    thickness = 0.3.dp,
                    lineCount = 5,
                    color = SolidColor(Color.LightGray)
                )
            )
        )
    }
}


