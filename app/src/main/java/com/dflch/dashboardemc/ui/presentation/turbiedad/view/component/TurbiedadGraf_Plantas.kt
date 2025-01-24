package com.dflch.dashboardemc.ui.presentation.turbiedad.view.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.PopupProperties

@Composable
fun TurbiedadGraf_Plantas(
    turbiedadP1: List<LecturasPlantas>,
    turbiedadP2: List<LecturasPlantas>
){

    var turbiedadPlanta = remember {  mutableListOf<LecturasTurbiedad>() }
    turbiedadPlanta.addAll(turbiedadP1.map { LecturasTurbiedad("P1", it.fecha, it.hora, it.lectura) })
    turbiedadPlanta.addAll(turbiedadP2.map { LecturasTurbiedad("P2", it.fecha, it.hora, it.lectura) })
    //turbiedadPlanta = turbiedadPlanta.distinctBy { Pair( it.fecha, it.hora) }.toMutableList()
    //turbiedadPlanta = turbiedadPlanta.distinctBy { ( it.fecha) }.toMutableList()

    turbiedadPlanta = turbiedadPlanta.sortedByDescending { it.fecha }.toMutableList()

    var turbiedadLectura01 =  remember {  mutableListOf<Double>() }
    var turbiedadLectura02 =  remember {  mutableListOf<Double>() }

    turbiedadLectura01.addAll(turbiedadP1.map { it.lectura })
    turbiedadLectura02.addAll(turbiedadP2.map { it.lectura })

    var horas = remember { mutableListOf<String>() }
    horas = generarDDHHDesdeObjetos(turbiedadPlanta).toMutableList()
    horas = horas.distinct().toMutableList()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.secondary)
            Text(
                text = "Turbiedad Lineal Plantas (UNT)",
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )

            LineChart(
                labelProperties = LabelProperties(
                    enabled = false,
                    labels = horas,
                    forceRotation = true,
                    rotationDegreeOnSizeConflict = -180f,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.DarkGray,
                        fontSize = 10.sp
                    )
                ),

                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 22.dp),
                data = remember {
                    listOf(

                        /*
                    Line(
                        label = "Turbiedad P1 UNT",
                        //values = turbiedadPlanta.filter { it.planta == "P1" }.map { it.lectura }.reversed(),
                        values = turbiedadLectura01.reversed(),
                        color = SolidColor(Color(0xFFff7043)),
                        firstGradientFillColor = Color(0xFFff7043).copy(alpha = .5f),
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
                        label = "Turbiedad P2 UNT",
                        //values = turbiedadPlanta.filter { it.planta == "P2" }.map { it.lectura },
                        values = turbiedadLectura02.reversed(),
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

                    */
                        Line(
                            label = "",
                            values = turbiedadPlanta.map { it.lectura }.reversed(),
                            color = SolidColor(Color(0xFFff7043)),
                            curvedEdges = true,
                            dotProperties = DotProperties(
                                enabled = true,
                                color = SolidColor(Color.White),
                                strokeWidth = 2.dp,
                                radius = 2.dp,
                                strokeColor = SolidColor(Color.DarkGray),
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
                    contentBuilder = { value -> "%.2f".format(value) + " UNT" }
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
}

data class LecturasTurbiedad(
    val planta: String,
    val fecha: String,
    val hora: Int,
    val lectura: Double
)

fun generarDDHHDesdeObjetos(lista: List<LecturasTurbiedad>): List<String> {
    return lista.map { lectura ->
        val partes = lectura.fecha.split(" ")
        val dia = partes[0].split("/")[1]
        val hora = partes[1].split(":")[0]

        "$dia-$hora"
    }
}
