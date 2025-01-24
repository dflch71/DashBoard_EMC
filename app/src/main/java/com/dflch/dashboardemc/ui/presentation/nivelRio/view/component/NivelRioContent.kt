package com.dflch.dashboardemc.ui.presentation.nivelRio.view.component

import android.icu.lang.UCharacter.toUpperCase
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dflch.dashboardemc.core.utils.Utility.Companion.extractDateTimeParts
import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas

@Composable
fun NivelRioContent(niveles: List<LecturasPlantas>) {

    // Estado para manejar la visibilidad del diálogo
    var showDialog by remember { mutableStateOf(false) }

    // Estado para manejar el elemento seleccionado (opcional si es necesario)
    var selectedNivel by remember { mutableStateOf<LecturasPlantas?>(null) }


    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(niveles.distinctBy { it.hora }) { nivel ->
            NivelRioItem(
                nivel,
                onClick = {
                    selectedNivel = nivel
                    showDialog = true
                })
        }
    }

    if (showDialog && selectedNivel != null) {
        val (datePart, timePart) = extractDateTimeParts(selectedNivel!!.fecha)

        val alertaAmarilla = if (selectedNivel!!.lectura <= 907.52) 907.52 - selectedNivel!!.lectura else 0.0
        val alertaNaranja = if (selectedNivel!!.lectura <= 908.52) 908.52 - selectedNivel!!.lectura else 0.0
        val alertaRoja = if (selectedNivel!!.lectura <= 909.50) 909.50 - selectedNivel!!.lectura else 0.0

        DialogWithImage(
            onDismissRequest = { showDialog = false },
            onConfirmation = { showDialog = false },
            fecha = (toUpperCase(datePart?.toString() ?: "Fecha") + " - " + timePart?.toString()),
            nivel = selectedNivel!!.lectura.toString(),
            alertaAmarilla = String.format("%.2f", alertaAmarilla) + " Mts ",
            alertaNaranja = String.format("%.2f", alertaNaranja) + " Mts ",
            alertaRoja = String.format("%.2f", alertaRoja) + " Mts "
        )
    }
}