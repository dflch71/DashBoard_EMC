package com.dflch.dashboardemc.ui.presentation.nivelRio.view

import android.icu.lang.UCharacter.toUpperCase
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dflch.dashboardemc.R
import com.dflch.dashboardemc.domain.model.lecturas.LecturasPlantas
import com.dflch.dashboardemc.ui.presentation.nivelRio.view.component.AlertDialogNivelRio
import com.dflch.dashboardemc.ui.presentation.nivelRio.view.component.NivelRioContent
import com.dflch.dashboardemc.ui.presentation.nivelRio.view.component.NivelRioGraf
import com.dflch.dashboardemc.ui.presentation.nivelRio.view.component.NivelRioGrafNivel
import com.dflch.dashboardemc.ui.presentation.nivelRio.viewmodel.NivelRioViewModel
import com.dflch.dashboardemc.ui.presentation.nivelRio.viewmodel.UiState
import kotlinx.coroutines.delay

@Composable
fun NivelRioScreen(
    nivelRioViewModel: NivelRioViewModel
) {
    val uiState by nivelRioViewModel.uiState.collectAsState()
    var isChangeGraph by remember { mutableStateOf(true) }
    var isErrorMsg by remember { mutableStateOf(false) }
    var msgError by remember { mutableStateOf("") }

    val status by nivelRioViewModel.status.collectAsState()

    LaunchedEffect(Unit) {
        while (true) {
            //nivelRioViewModel.refreshDataNivelRio()
            nivelRioViewModel.fetchNivelRio()
            delay(300000L) // Actualiza cada 5 minutos
        }
    }

    when (uiState) {
        is UiState.Loading -> {
            Box(modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is UiState.Success -> {
            val niveles = (uiState as UiState.Success<List<LecturasPlantas>>).data
            // Muestra la lista de niveles
            Box(modifier = Modifier.fillMaxSize()) {
                if (niveles.isNotEmpty()) {
                    Column {
                        Box(
                            modifier = Modifier.weight(0.12f)
                        ) {
                            Row {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(0.85f)
                                ) {
                                    Text(
                                        text = "Niveles de Rio M.S.N.M",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(2.dp),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center
                                    )

                                    Text(
                                        text = toUpperCase("${niveles[0].fecha} - ${niveles[niveles.size - 1].fecha}"),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(2.dp),
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center
                                    )
                                }

                                /*
                                IconButton(
                                    onClick = { isChangeGraph = !isChangeGraph },
                                    modifier = Modifier
                                        .weight(0.15f),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = Color.LightGray,
                                        contentColor = Color.Black
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_chart_outlined),
                                        contentDescription = null
                                    )
                                }
                                */
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(0.20f)
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(4.dp)
                            ) {
                                val nivelesUnicos = niveles.distinctBy { it.fecha }
                                NivelRioContent(nivelesUnicos)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1.0f)
                                .clickable(onClick = { isChangeGraph = !isChangeGraph })
                        ){
                            if (isChangeGraph) { NivelRioGrafNivel(niveles) }
                            else { NivelRioGraf(niveles) }
                        }
                    }
                } else {
                    // Si la lista está vacía, muestra un mensaje
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Text(
                                text = "No hay datos disponibles, la información no se ha SINCRONIZADO desde el dispositivo móvil.",
                                color = Color.Gray,
                                modifier = Modifier.padding(14.dp),
                                style = MaterialTheme.typography.labelLarge,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = status,
                                color = Color.Gray,
                                modifier = Modifier.padding(14.dp),
                                style = MaterialTheme.typography.labelLarge,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
        is UiState.Error -> {
            //val errorMessage = (uiState as UiState.Error).message
            //Text(text = "Error: $errorMessage", color = Color.Red)
            msgError = (uiState as UiState.Error).message
            isErrorMsg = true
        }
    }

    if (isErrorMsg) {
        Text(
            text = msgError,
            color = Color.Red,
            modifier = Modifier.padding(14.dp),
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center
        )
        AlertDialogNivelRio(nivelRioViewModel, msgError)
    }
}

