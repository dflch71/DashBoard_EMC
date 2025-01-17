package com.dflch.dashboardemc.ui.presentation.turbiedad.view

import android.icu.lang.UCharacter.toUpperCase
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dflch.dashboardemc.R
import com.dflch.dashboardemc.domain.model.nivelrio.LecturasPlantas
import com.dflch.dashboardemc.ui.components.CharType
import com.dflch.dashboardemc.ui.presentation.turbiedad.view.component.AlertDialogTurbiedad
import com.dflch.dashboardemc.ui.presentation.turbiedad.view.component.TurbiedadContent
import com.dflch.dashboardemc.ui.presentation.turbiedad.view.component.TurbiedadGraf
import com.dflch.dashboardemc.ui.presentation.turbiedad.view.component.TurbiedadGrafColumn
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.TurbiedadViewModel
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.UiStateP1
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TurbiedadPlanta01Screen(
    turbiedadViewModel : TurbiedadViewModel,
    navController: NavController
) {
    val uiState by turbiedadViewModel.uiStateP1.collectAsState()
    var turbiedad: List<LecturasPlantas> by remember { mutableStateOf(emptyList()) }

    // Actualiza los datos cada 120 segundos
    LaunchedEffect(Unit) {
        while (true) {
            turbiedadViewModel.refreshDataTurbiedadP1()
            delay(900000L) // Actualiza cada 120 segundos
        }
    }

    var selectedButton by remember { mutableStateOf("Línea") }
    var selectedChart by remember { mutableStateOf<CharType?>(CharType.Line) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Turbiedad Plantas (UNT)",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {},
                scrollBehavior = scrollBehavior
            )
        },


        bottomBar = {
            BottomAppBar(actions = {

                Spacer(modifier = Modifier.width(12.dp))


                IconButton(onClick = {
                    selectedButton = "Línea"
                    selectedChart = CharType.Line
                }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_line_chart),
                        contentDescription = "Build description"
                    )
                }

                IconButton(onClick = {
                    selectedButton = "Barra"
                    selectedChart = CharType.Bar
                }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bar_chart),
                        contentDescription = "Menu description",
                    )
                }
            })
        }

    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            when (uiState) {
                is UiStateP1.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UiStateP1.Success -> {
                    turbiedad = (uiState as UiStateP1.Success<List<LecturasPlantas>>).data
                    if (turbiedad.isNotEmpty()) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 12.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box (modifier = Modifier.weight(0.25f)) {
                                turbiedadPlanta01(turbiedad, turbiedadViewModel)
                            }

                            Box (modifier = Modifier.weight(0.75f)) {
                                AnimatedContent(
                                    targetState = selectedChart,
                                    transitionSpec = { fadeIn(animationSpec = tween(1000)) with fadeOut(animationSpec = tween(1000)) },
                                    //transitionSpec = { slideInHorizontally() + fadeIn() with slideOutHorizontally() + fadeOut() }
                                    //transitionSpec = { scaleIn(initialScale = 0.8f) + fadeIn() with scaleOut(targetScale = 1.2f) + fadeOut() }
                                ) { selectedChart ->
                                    when (selectedChart) {
                                        CharType.Line -> {
                                            TurbiedadGraf(turbiedad)
                                        }

                                        CharType.Bar -> {
                                            TurbiedadGrafColumn(turbiedad)
                                        }

                                        else -> {}
                                    }
                                }
                            }
                        }

                    }
                }

                is UiStateP1.Error -> {
                    val errorMessage = (uiState as UiStateP1.Error).message
                    Text(text = "Error: $errorMessage", color = Color.Red)
                }
            }
        }
    }
}

@Composable
private fun turbiedadPlanta01(
    turbiedadP1: List<LecturasPlantas>,
    turbiedadViewModel: TurbiedadViewModel
) {
    //var isChangeGraph by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
    ) {
        if (turbiedadP1.isNotEmpty()) {
            Column {
                Text(
                    text = "Turbiedad Planta 01 (UNT)",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = toUpperCase("${turbiedadP1[0].fecha} - ${turbiedadP1[turbiedadP1.size - 1].fecha}"),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Box(modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(4.dp)
                    ) {
                        val turbiedadP1Unicos = turbiedadP1.distinctBy { it.fecha }
                        TurbiedadContent(turbiedadP1Unicos)
                    }
                }
            }
        } else {
            AlertDialogTurbiedad(turbiedadViewModel)
        }
    }
}









