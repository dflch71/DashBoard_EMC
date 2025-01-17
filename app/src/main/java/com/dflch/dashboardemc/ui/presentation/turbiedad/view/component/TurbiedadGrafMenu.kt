package com.dflch.dashboardemc.ui.presentation.turbiedad.view.component

import androidx.annotation.Nullable
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dflch.dashboardemc.domain.model.nivelrio.LecturasPlantas
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.IndicatorPosition
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.PopupProperties
import ir.ehsannarmani.compose_charts.models.StrokeStyle

@Composable
fun TurbiedadGrafMenu(turbiedadP1: List<LecturasPlantas>) {
    var horas = mutableListOf<String>()
    var turbiedadLectura = mutableListOf<Double>()
    var horasLectura = 0
    var lastHour = 0

    if (turbiedadP1.isEmpty()) return

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

    val hora01 : String by remember { mutableStateOf(horas[0] ?: "")}
    val hora02 : String by remember { mutableStateOf(horas[1] ?: "")}
    val hora03 : String by remember { mutableStateOf(horas[2] ?: "")}
    val hora04 : String by remember { mutableStateOf(horas[3] ?: "")}
    val hora05 : String by remember { mutableStateOf(horas[4] ?: "")}
    val hora06 : String by remember { mutableStateOf(horas[5] ?: "")}

    val turbiedad1 : Double by remember { mutableStateOf(turbiedadLectura[0] ?: 0.0)}
    val turbiedad2 : Double by remember { mutableStateOf(turbiedadLectura[1] ?: 0.0)}
    val turbiedad3 : Double by remember { mutableStateOf(turbiedadLectura[2] ?: 0.0)}
    val turbiedad4 : Double by remember { mutableStateOf(turbiedadLectura[3] ?: 0.0)}
    val turbiedad5 : Double by remember { mutableStateOf(turbiedadLectura[4] ?: 0.0)}
    val turbiedad6 : Double by remember { mutableStateOf(turbiedadLectura[5] ?: 0.0)}

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ColumnChart(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp),

            labelProperties = LabelProperties(
                enabled = true,
                forceRotation = true,
                rotationDegreeOnSizeConflict = -360.0f,
                textStyle = TextStyle(color = Color.Black, fontSize = 12.sp),
            ),

            indicatorProperties = HorizontalIndicatorProperties(
                textStyle = TextStyle(color = Color.Black, fontSize = 11.sp),
            ),

            data = remember {
                listOf(

                    Bars(
                        label = hora01,
                        values = listOf(Bars.Data(value = turbiedad1, color = SolidColor(Color(0xFFE1BEE7)) ))
                    ),
                    Bars(
                        label = hora02,
                        values = listOf(Bars.Data(value = turbiedad2, color = SolidColor(Color(0xFFD1C4E9)) ))
                    ),
                    Bars(
                        label = hora03,
                        values = listOf(Bars.Data(value = turbiedad3, color = SolidColor(Color(0xFFC5CAE9)) ))
                    ),
                    Bars(
                        label = hora04,
                        values = listOf(Bars.Data(value = turbiedad4, color = SolidColor(Color(0xFFBBDEFB)) ))
                    ),
                    Bars(
                        label = hora05,
                        values = listOf(Bars.Data(value = turbiedad5, color = SolidColor(Color(0xFFB3E5FC)) ))
                    ),
                    Bars(
                        label = hora06,
                        values = listOf(Bars.Data(value = turbiedad6, color = SolidColor(Color(0xFFB2EBF2)) ))
                    ),
                )
            },


            barProperties = BarProperties(
                cornerRadius =  Bars.Data.Radius.Rectangle(topRight = 6.dp, topLeft = 6.dp),
                spacing = 3.dp,
                thickness = 20.dp
            ),

            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
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