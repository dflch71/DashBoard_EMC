package com.dflch.dashboardemc.ui.presentation.turbiedad.view.component

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
fun TurbiedadGraf(turbiedadP1: List<LecturasPlantas>) {

    var horas = remember {  mutableListOf<String>() }
    var turbiedadLectura =  remember {  mutableListOf<Double>() }

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
        modifier = Modifier.fillMaxSize()
    ) {
        LineChart(
            labelProperties = LabelProperties(
                enabled = true,
                labels = horas.reversed(),
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp),
            data = remember {
                listOf(
                    /*
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
                    */

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