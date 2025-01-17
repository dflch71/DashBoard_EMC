package com.dflch.dashboardemc.ui.presentation.turbiedad.view.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.DialogProperties
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.TurbiedadViewModel

@Composable
fun AlertDialogTurbiedad(viewModel: TurbiedadViewModel) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Error Cargando Información")
            },
            text = {
                Text(
                    "- No hay datos disponibles \n" +
                            "- Verifar la conexión a internet"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.refreshDataTurbiedadP1()
                        openDialog.value = false
                    }
                ) {
                    Text("Aceptar")
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Salir")
                }
            },

            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        )
    }
}