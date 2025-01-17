package com.dflch.dashboardemc.ui.presentation.nivelRio.view.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.DialogProperties
import com.dflch.dashboardemc.ui.presentation.nivelRio.viewmodel.NivelRioViewModel

@Composable
fun AlertDialogNivelRio(viewModel: NivelRioViewModel, msg: String) {

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
                Text( msg )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.fetchNivelRio()
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