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
import com.dflch.dashboardemc.core.utils.Utility.Companion.extractTimeAmPm
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
fun NivelRioGrafNivel(niveles: List<LecturasPlantas>) {
    var horas = mutableListOf<String>()
    var nivelesLectura = mutableListOf<Double>()

    horas.add(extractTimeAmPm(niveles[0].fecha) +" "+niveles[0].lectura.toString()+" M.S.N.M")
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
                        values = listOf(909.50, 909.50, 905.10, 905.10, 909.50, 909.50),
                        color = SolidColor(Color(0XFF795548)),
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
                        label = "Naranja\n(908.52+)", //908.52 - 909.49
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
                        label = "Amarilla\n(907.52+)", //900.00 - 908.51
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

            minValue = 905.00,
            maxValue = 911.00,

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