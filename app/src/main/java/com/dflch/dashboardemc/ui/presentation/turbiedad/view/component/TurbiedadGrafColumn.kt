package com.dflch.dashboardemc.ui.presentation.turbiedad.view.component

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
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.PopupProperties

@Composable
fun TurbiedadGrafColumn(turbiedadP1: List<LecturasPlantas>) {
    var horas = remember {   mutableListOf<String>() }
    var turbiedadLectura = remember { mutableListOf<Double>() }


    horas.add(extractTimeAmPm(turbiedadP1[0].fecha) +" "+turbiedadP1[0].lectura.toString()+" UNT")
    turbiedadLectura.add(turbiedadP1[0].lectura)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ColumnChart(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp),
            data = remember {                listOf(

                    Bars(
                        label = "Turbiedad: ${horas[0]}",
                        values = listOf(

                            Bars.Data( value = 0.0, color = SolidColor(Color.Transparent)),
                            Bars.Data( value = 0.0, color = SolidColor(Color.Transparent)),

                            Bars.Data(
                                //label = horas[horas.size-1],
                                label = turbiedadP1[0].lectura.toString()+" UNT",
                                value = turbiedadLectura[horas.size-1],
                                color = SolidColor(Color(0xFF2596be))
                            ),

                            Bars.Data( label ="         ", value = 0.0, color = SolidColor(Color.Transparent)),

                            Bars.Data(
                                label = "Amarilla (4000)",
                                value = 4000.00,
                                color = SolidColor(Color.Yellow)
                            ),

                            Bars.Data(
                                label = "Naranja (5000)",
                                value = 5000.00,
                                color = SolidColor(Color(0xFFFFA500))
                            ),

                            Bars.Data(
                                label = "Roja (6000+)",
                                value = 6000.00,
                                color = SolidColor(Color.Red)
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
                    enabled = true,
                    thickness = 0.3.dp,
                    lineCount = 5,
                    color = SolidColor(Color.LightGray)
                )
            )
        )
    }

}

