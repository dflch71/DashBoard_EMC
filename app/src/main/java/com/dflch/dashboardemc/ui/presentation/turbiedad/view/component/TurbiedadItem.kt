package com.dflch.dashboardemc.ui.presentation.turbiedad.view.component

import android.icu.lang.UCharacter.toUpperCase
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dflch.dashboardemc.R
import com.dflch.dashboardemc.core.utils.Utility.Companion.extractDateTimeParts
import com.dflch.dashboardemc.domain.model.nivelrio.LecturasPlantas

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