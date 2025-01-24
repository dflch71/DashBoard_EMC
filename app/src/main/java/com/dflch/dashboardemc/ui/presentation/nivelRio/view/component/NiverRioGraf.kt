package com.dflch.dashboardemc.ui.presentation.nivelRio.view.component

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.PopupProperties

@Composable
fun NivelRioGraf(niveles: List<LecturasPlantas>) {
    var horas = mutableListOf<String>()
    var nivelesLectura = mutableListOf<Double>()
    var horasLectura = 0
    var lastHour = 0

    for (nivel in niveles.distinctBy { it.hora }) {
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
