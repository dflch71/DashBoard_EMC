package com.dflch.dashboardemc.ui.components

import android.icu.lang.UCharacter.toUpperCase
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TituloFecha(fecha01: String, fecha02: String)  {
    Text(
        text = toUpperCase("${fecha01} - ${fecha02}"),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.labelMedium,
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}