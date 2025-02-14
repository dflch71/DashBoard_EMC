package com.dflch.dashboardemc.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.dflch.dashboardemc.core.utils.Utility.Companion.extractDateTimeParts

@Composable
fun DatePart(fecha: String){
    val (datePart, timePart) = extractDateTimeParts(fecha) // Or extractDateTimePartsRegex(text)
    val fechaHora = "$datePart $timePart"
    Text(
        text = fechaHora,
        maxLines = 1,
        fontSize = 10.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}