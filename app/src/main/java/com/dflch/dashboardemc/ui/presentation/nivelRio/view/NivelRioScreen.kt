package com.dflch.dashboardemc.ui.presentation.nivelRio.view

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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.dflch.dashboardemc.R
import com.dflch.dashboardemc.core.utils.Utility.Companion.extractDateDay
import com.dflch.dashboardemc.core.utils.Utility.Companion.extractDateTimeParts
import com.dflch.dashboardemc.core.utils.Utility.Companion.extractTimeAmPm
import com.dflch.dashboardemc.domain.model.nivelrio.LecturasPlantas
import com.dflch.dashboardemc.ui.presentation.nivelRio.viewmodel.NivelRioViewModel
import com.dflch.dashboardemc.ui.presentation.nivelRio.viewmodel.UiState
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


@Composable
fun NivelRioScreen(
    nivelRioViewModel: NivelRioViewModel
) {
    val uiState by nivelRioViewModel.uiState.collectAsState()
    var isChangeGraph by remember { mutableStateOf(false) }
    var isErrorMsg by remember { mutableStateOf(false) }
    var msgError by remember { mutableStateOf("") }

    val status by nivelRioViewModel.status.collectAsState()

    LaunchedEffect(Unit) {
        while (true) {
            //nivelRioViewModel.refreshDataNivelRio()
            nivelRioViewModel.fetchNivelRio()
            delay(300000L) // Actualiza cada 5 minutos
        }
    }

    when (uiState) {
        is UiState.Loading -> {
            Box(modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is UiState.Success -> {
            val niveles = (uiState as UiState.Success<List<LecturasPlantas>>).data
            // Muestra la lista de niveles
            Box(modifier = Modifier.fillMaxSize()) {
                if (niveles.isNotEmpty()) {
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
                                        text = "Niveles de Rio M.S.N.M",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(2.dp),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center
                                    )

                                    Text(
                                        text = toUpperCase("${niveles[0].fecha} - ${niveles[niveles.size - 1].fecha}"),
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
                                val nivelesUnicos = niveles.distinctBy { it.fecha }
                                NivelRioContent(nivelesUnicos)
                            }
                        }

                        Box(modifier = Modifier.weight(1.0f)) {
                            if (isChangeGraph) { NivelRioGrafNivel(niveles) }
                            else { NivelRioGraf(niveles) }
                        }
                    }
                } else {
                    // Si la lista está vacía, muestra un mensaje
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Text(
                                text = "No hay datos disponibles, la información no se ha SINCRONIZADO desde el dispositivo móvil.",
                                color = Color.Gray,
                                modifier = Modifier.padding(14.dp),
                                style = MaterialTheme.typography.labelLarge,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = status,
                                color = Color.Gray,
                                modifier = Modifier.padding(14.dp),
                                style = MaterialTheme.typography.labelLarge,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
        is UiState.Error -> {
            //val errorMessage = (uiState as UiState.Error).message
            //Text(text = "Error: $errorMessage", color = Color.Red)
            msgError = (uiState as UiState.Error).message
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
        AlertDialogNiverRio(nivelRioViewModel, msgError)
    }
}

@Composable
fun AlertDialogNiverRio(viewModel: NivelRioViewModel, msg: String) {

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
                Text( msg )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.fetchNivelRio()
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
fun NivelRioContent(niveles: List<LecturasPlantas>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(niveles) { nivel ->
            NivelRioItem(nivel)
        }
    }
}

@Composable
fun NivelRioItem(nivel: LecturasPlantas) {
    Column(
        modifier = Modifier
            .width(90.dp)
            .wrapContentHeight()
            .background(
                color = if (nivel.lectura < 907.52) colorResource(id = R.color.purple_500) //Azul
                else if (nivel.lectura in 907.52..908.51) Color.Yellow
                else if (nivel.lectura in 908.52..909.49) Color(0xFFFFA500)
                else Color.Red,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = nivel.lectura.toString(),
            maxLines = 1,
            fontSize = 14.sp,
            color = if (nivel.lectura in 907.52..908.51) Color.Black else Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        val (datePart, timePart) = extractDateTimeParts(nivel.fecha) // Or extractDateTimePartsRegex(text)

        Text(
            text = toUpperCase(datePart?.toString() ?: "Fecha"),
            maxLines = 1,
            fontSize = 10.sp,
            color = if (nivel.lectura in 907.52..908.51) Color.Black else Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = timePart?.toString() ?: "Hora",
            maxLines = 1,
            fontSize = 10.sp,
            color = if (nivel.lectura in 907.52..908.51) Color.Black else Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NivelRioGraf(niveles: List<LecturasPlantas>) {
    var horas = mutableListOf<String>()
    var nivelesLectura = mutableListOf<Double>()
    var horasLectura = 0
    var lastHour = 0

    for (nivel in niveles) {
        horasLectura++
        if (horas.size >= 10) break

        if (horasLectura < 30) {
            if (horasLectura == 1) {
                lastHour = nivel.hora
                horas.add(nivel.hora.toString())
                nivelesLectura.add(nivel.lectura)
            } else {
                if (lastHour != nivel.hora) {
                    lastHour = nivel.hora
                    horas.add(nivel.hora.toString())
                    nivelesLectura.add(nivel.lectura)
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
                        label = "Roja\n(909.50+)", //909.50
                        values = listOf(909.50, 909.50, 909.50, 909.50, 909.50),
                        color = SolidColor(Color.Red),
                        firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                        drawStyle = DrawStyle.Stroke(width = 2.dp)
                    ),

                    Line(
                        label = "Naranja\n(908.52)", //908.52 - 909.49
                        values = listOf(908.52, 908.52, 908.52, 908.52, 908.52),
                        color =  SolidColor(Color(0xFFFFA500)),
                        firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                        drawStyle = DrawStyle.Stroke(width = 2.dp)
                    ),

                    Line(
                        label = "Amarilla\n(907.52)", //900.00 - 908.51
                        values = listOf(907.52, 907.52, 907.52,907.52, 907.52,907.52, ),
                        color = SolidColor(Color.Yellow),
                        firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                        drawStyle = DrawStyle.Stroke(width = 2.dp)
                    ),

                    Line(
                        label = "Nivel Rio",
                        //values = listOf(28.0, 41.0, 5.0, 10.0, 35.0),
                        values = nivelesLectura.reversed(),
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

            minValue = 901.00,
            maxValue = 915.00,

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
                contentBuilder = { value-> "%.2f".format(value)+" m.s.n.m" }
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
fun NivelRioGrafNivel(niveles: List<LecturasPlantas>) {
    var horas = mutableListOf<String>()
    var nivelesLectura = mutableListOf<Double>()

    horas.add(extractTimeAmPm(niveles[0].fecha)+" "+niveles[0].lectura.toString()+" M.S.N.M")
    nivelesLectura.add(niveles[0].lectura)
    val nivelRio = niveles[0].lectura

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LineChart(
            labelProperties = LabelProperties(
                enabled = true,
                labels = horas
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp),
            data = remember {
                listOf(

                    Line(
                        label = "Cauce", //909.50
                        values = listOf(909.50, 909.50, 900.50, 900.50, 909.50, 909.50),
                        color = SolidColor(Color(0XFFFF795548)),
                        firstGradientFillColor = Color(0xFFFF80AB).copy(alpha = .5f),
                        drawStyle = DrawStyle.Stroke(width = 2.dp),
                    ),

                    Line(
                        label = "Roja\n(909.50+)", //909.50
                        values = listOf(909.50, 909.50, 909.50, 909.50, 909.50, 909.50),
                        color = SolidColor(Color.Red),
                        //firstGradientFillColor = Color(0xFFF4511E).copy(alpha = .05f),
                        drawStyle = DrawStyle.Stroke(width = 2.dp),
                        secondGradientFillColor = Color.Transparent,
                        strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                        gradientAnimationDelay = 1000,
                        dotProperties = DotProperties(
                            enabled = true,
                            color = SolidColor(Color.White),
                            strokeWidth = 1.dp,
                            radius = 1.dp,
                            strokeColor = SolidColor(Color.LightGray),
                        )
                    ),

                    Line(
                        label = "Naranja\n(908.52)", //908.52 - 909.49
                        values = listOf(908.52, 908.52, 908.52, 908.52, 908.52, 908.52,),
                        color =  SolidColor(Color(0xFFFFA500)),
                        //firstGradientFillColor = Color(0xFFFFEE58).copy(alpha = .05f),
                        drawStyle = DrawStyle.Stroke(width = 2.dp),
                        secondGradientFillColor = Color.Transparent,
                        strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                        gradientAnimationDelay = 1000,
                        dotProperties = DotProperties(
                            enabled = true,
                            color = SolidColor(Color.White),
                            strokeWidth = 1.dp,
                            radius = 1.dp,
                            strokeColor = SolidColor(Color.LightGray),
                        )
                    ),

                    Line(
                        label = "Amarilla\n(907.52)", //900.00 - 908.51
                        values = listOf(907.52, 907.52, 907.52, 907.52, 907.52, 907.52,),
                        color = SolidColor(Color.Yellow),
                        //firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                        drawStyle = DrawStyle.Stroke(width = 2.dp),
                        secondGradientFillColor = Color.Transparent,
                        strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                        gradientAnimationDelay = 1000,
                        dotProperties = DotProperties(
                            enabled = true,
                            color = SolidColor(Color.White),
                            strokeWidth = 1.dp,
                            radius = 1.dp,
                            strokeColor = SolidColor(Color.LightGray),
                        )
                    ),

                    Line(
                        label = "Nivel Rio",
                        values = listOf(nivelRio, nivelRio, nivelRio, nivelRio, nivelRio, nivelRio),
                        color = SolidColor(Color(0xFF2596be)),
                        firstGradientFillColor = Color(0xFF2596be).copy(alpha = .5f),
                        secondGradientFillColor = Color.Transparent,
                        strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                        gradientAnimationDelay = 1000,
                        drawStyle = DrawStyle.Stroke(width = 3.dp),
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

            minValue = 900.00,
            maxValue = 915.00,

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
                contentBuilder = { value-> "%.2f".format(value)+" m.s.n.m" }
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
