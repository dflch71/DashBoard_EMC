package com.dflch.dashboardemc.ui.presentation.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dflch.dashboardemc.R
import com.dflch.dashboardemc.ui.components.BottomNavigationBar
import com.dflch.dashboardemc.ui.presentation.niveltanque.view.NivelTanqueScreen
import com.dflch.dashboardemc.ui.presentation.irca.view.IrcaScreen
import com.dflch.dashboardemc.ui.presentation.network.viewmodel.NetworkViewModel
import com.dflch.dashboardemc.ui.presentation.nivelRio.view.NivelRioScreen
import com.dflch.dashboardemc.ui.presentation.nivelRio.viewmodel.NivelRioViewModel
import com.dflch.dashboardemc.ui.presentation.niveltanque.viewModel.NivelTanqueViewModel
import com.dflch.dashboardemc.ui.presentation.turbiedad.view.TurbiedadScreen
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.TurbiedadViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    networkViewModel: NetworkViewModel,
    nivelRioViewModel: NivelRioViewModel,
    turbiedadViewModel: TurbiedadViewModel,
    nivelTanqueViewModel: NivelTanqueViewModel,
    //ircaViewModel: IrcaViewModel
) {

    val isNetworkAvailable by networkViewModel.networkStatus.collectAsState()
    val isConnect = remember { mutableStateOf(true) }
    val snackbarHostState = remember { SnackbarHostState() }  // Para el Snackbar

    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { DashBoardTopAppBar(isConnect) },

        bottomBar = {
            BottomNavigationBar(
                onItemselected = { actualIndex ->
                    selectedItemIndex = actualIndex
                }
            )
        },

        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(align = Alignment.TopCenter)
                    .statusBarsPadding()

            )
        }

    ) { paddingValues ->

        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
        ) {
            if (!isNetworkAvailable) {
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar(
                        message = "No hay Conexión a Internet",
                        withDismissAction = true
                    )
                }
            }

            isConnect.value = isNetworkAvailable

            Column (
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)
            ) {
                when (selectedItemIndex) {
                    0 -> { NivelRioScreen(nivelRioViewModel) }
                    1 -> { TurbiedadScreen(turbiedadViewModel) }
                    2 -> { NivelTanqueScreen(nivelTanqueViewModel) }
                    3 -> { IrcaScreen() }
                }
            }

            //Pendiente de implementar para consultas del Día, Semana, Mes
            /*SmallFloatingActionButton(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                onClick = { showBottomSheet = true }
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }*/
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Sheet content
                    var selectedIndex by remember { mutableStateOf(0) }
                    val options = listOf("Día", "Semana", "Mes")
                    SingleChoiceSegmentedButtonRow {
                        options.forEachIndexed { index, label ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = options.size
                                ),
                                onClick = { selectedIndex = index },
                                selected = index == selectedIndex
                            ) {
                                Text(label)
                            }
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(top = 24.dp),
                        color = MaterialTheme.colorScheme.outline
                    )

                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(4.dp),
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        }) { Text("Cerrar") }
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardTopAppBar(
    hayRed: MutableState<Boolean>
) {

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("Dashboard EMC")
        },

        actions = {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(end = 4.dp)
            ) {
                Icon(
                    painter = painterResource(if (hayRed.value) R.drawable.ic_wifi_on else R.drawable.ic_wifi_off),
                    contentDescription = null
                )
            }

        }
    )

}







