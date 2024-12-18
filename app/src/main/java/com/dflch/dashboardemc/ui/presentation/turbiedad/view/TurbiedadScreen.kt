package com.dflch.dashboardemc.ui.presentation.turbiedad.view

import android.icu.lang.UCharacter.toUpperCase
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.dflch.dashboardemc.R
import com.dflch.dashboardemc.core.utils.Utility.Companion.extractDateDay
import com.dflch.dashboardemc.core.utils.Utility.Companion.extractDateTimeParts
import com.dflch.dashboardemc.core.utils.Utility.Companion.extractTimeAmPm
import com.dflch.dashboardemc.domain.model.nivelrio.LecturasPlantas
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.TurbiedadViewModel
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.UiState
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.PopupProperties
import kotlinx.coroutines.delay

@Composable
fun TurbiedadScreen(
    turbiedadViewModel : TurbiedadViewModel
) {
    val uiState by turbiedadViewModel.uiState.collectAsState()
    var isChangeGraph by remember { mutableStateOf(false) }

    // Actualiza los datos cada 60 segundos
    LaunchedEffect(Unit) {
        while (true) {
            turbiedadViewModel.refreshDataTurbiedadP1()
            delay(300000L) // Actualiza cada 60 segundos
        }
    }

    when (uiState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is UiState.Success -> {
            val turbiedadP1 = (uiState as UiState.Success<List<LecturasPlantas>>).data
            // Muestra la lista de turbiedad planta 1
            Box(modifier = Modifier.fillMaxSize()) {
                if (turbiedadP1.isNotEmpty()) {
                    Column {
                        Box(
                            modifier = Modifier.weight(0.12f)
                        ) {
                            Row {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(0.85f)
                                ) {
                                    Text(
                                        text = "Turbiedad Planta 01 (UNT)",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(2.dp),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center
                                    )

                                    Text(
                                        text = toUpperCase("${turbiedadP1[0].fecha} - ${turbiedadP1[turbiedadP1.size - 1].fecha}"),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(2.dp),
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center
                                    )
                                }

                                IconButton(
                                    onClick = { isChangeGraph = !isChangeGraph },
                                    modifier = Modifier
                                        .weight(0.15f),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = Color.LightGray,
                                        contentColor = Color.Black
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_chart_outlined),
                                        contentDescription = null
                                    )
                                }
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
                                val turbiedadP1Unicos = turbiedadP1.distinctBy { it.fecha }
                                TurbiedadContent(turbiedadP1Unicos)
                            }
                        }

                        Box(modifier = Modifier.weight(1.0f)) {
                            if (isChangeGraph) {
                                TurbiedadGrafColumn(turbiedadP1)
                            } else {
                                TurbiedadGraf(turbiedadP1)
                            }
                        }
                    }
                    } else {
                        AlertDialogTurbiedad(turbiedadViewModel)
                    }

            }
        }
        is UiState.Error -> {
            val errorMessage = (uiState as UiState.Error).message
            Text(text = "Error: $errorMessage", color = Color.Red)
        }
    }
}

@Composable
fun AlertDialogTurbiedad(viewModel: TurbiedadViewModel) {
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
                        viewModel.refreshDataTurbiedadP1()
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
fun TurbiedadContent(turbiedadP1: List<LecturasPlantas>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(turbiedadP1) { turbiedad ->
            TurbiedadItem(turbiedad)
        }
    }
}

@Composable
fun TurbiedadItem(turbiedad: LecturasPlantas) {
    Column(
        modifier = Modifier
            .width(90.dp)
            .wrapContentHeight()
            .background(
                color = if (turbiedad.lectura < 4000) colorResource(id = R.color.purple_500) //Azul
                else if (turbiedad.lectura in 4000.00..4999.00) Color.Yellow
                else if (turbiedad.lectura in 5000.00..5999.00) Color(0xFFFFA500)
                else Color.Red,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = turbiedad.lectura.toString(),
            maxLines = 1,
            fontSize = 14.sp,
            color = if (turbiedad.lectura in 4000.00..4999.00) Color.Black else Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        val (datePart, timePart) = extractDateTimeParts(turbiedad.fecha) // Or extractDateTimePartsRegex(text)

        Text(
            text = toUpperCase(datePart?.toString() ?: "Fecha"),
            maxLines = 2,
            fontSize = 10.sp,
            color = if (turbiedad.lectura in 4000.00..4999.00) Color.Black else Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = timePart?.toString() ?: "Hora",
            maxLines = 1,
            fontSize = 10.sp,
            color = if (turbiedad.lectura in 4000.00..4999.00) Color.Black else Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TurbiedadGraf(turbiedadP1: List<LecturasPlantas>) {
    var horas = mutableListOf<String>()
    var turbiedadLectura = mutableListOf<Double>()
    var horasLectura = 0
    var lastHour = 0

    for (turbiedad in turbiedadP1)  {
        horasLectura++
        if (horas.size >= 10) break

        if (horasLectura < 30) {
            if (horasLectura == 1) {
                lastHour = turbiedad.hora
                horas.add(turbiedad.hora.toString())
                turbiedadLectura.add(turbiedad.lectura)
            } else {
                if (lastHour != turbiedad.hora) {
                    lastHour = turbiedad.hora
                    horas.add(turbiedad.hora.toString())
                    turbiedadLectura.add(turbiedad.lectura)
                }
            }
        }
    }

    Box(
        modifier = Modifier
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
                        label = "Roja (6000+)", //909.50
                        values = listOf(0.0),
                        color = SolidColor(Color.Red),
                        //firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                        //drawStyle = DrawStyle.Stroke(width = 2.dp)
                    ),

                    Line(
                        label = "Naranja (5000)", //908.52 - 909.49
                        values = listOf(0.0),
                        color =  SolidColor(Color(0xFFFFA500)),
                        //firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                        //drawStyle = DrawStyle.Stroke(width = 2.dp)
                    ),

                    Line(
                        label = "Amarilla (4000)", //4000.00 - 4999.99
                        values = listOf(0.0),
                        color = SolidColor(Color.Yellow),
                        //firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                        //drawStyle = DrawStyle.Stroke(width = 2.dp)
                    ),

                    Line(
                        label = "Turbiedad UNT",
                        values = turbiedadLectura.reversed(),
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
                contentBuilder = { value-> "%.2f".format(value)+" UNT" }
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

@Composable
fun TurbiedadGrafColumn(turbiedadP1: List<LecturasPlantas>) {
    var horas = mutableListOf<String>()
    var turbiedadLectura = mutableListOf<Double>()


    horas.add(extractTimeAmPm(turbiedadP1[0].fecha)+" "+turbiedadP1[0].lectura.toString()+" UNT")
    turbiedadLectura.add(turbiedadP1[0].lectura)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ColumnChart(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp),
            data = remember {
                listOf(
                    Bars(
                        label = "Turbiedad: ${horas[0]}",
                        values = listOf(
                            Bars.Data(
                                label = "Roja (6000+)",
                                value = 6000.00,
                                color = SolidColor(Color.Red)
                            ),
                            Bars.Data(
                                label = "Naranja (5000)",
                                value = 5000.00,
                                color = SolidColor(Color(0xFFFFA500))
                            ),
                            Bars.Data(
                                label = "Amarilla (4000)",
                                value = 4000.00,
                                color = SolidColor(Color.Yellow)
                            ),
                            Bars.Data(
                                label = horas[horas.size-1],
                                value = turbiedadLectura[horas.size-1],
                                color = SolidColor(Color(0xFF2596be))
                            ),

                        ),
                    )
                )
            },

            barProperties = BarProperties(
                spacing = 10.dp,
                thickness = 20.dp,
                cornerRadius = Bars.Data.Radius.Circular(5.dp),
            ),

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
                contentBuilder = { value-> "%.2f".format(value)+" UNT" }
            ),

            gridProperties = GridProperties(
                xAxisProperties = GridProperties.AxisProperties(
                    enabled = false,
                    thickness = 0.3.dp,
                    lineCount = 5,
                    color = SolidColor(Color.LightGray)
                )
            )
        )
    }

}

