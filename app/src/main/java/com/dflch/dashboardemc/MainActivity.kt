package com.dflch.dashboardemc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dflch.dashboardemc.ui.container.ScreenContainer
import com.dflch.dashboardemc.ui.presentation.network.viewmodel.NetworkViewModel
import com.dflch.dashboardemc.ui.presentation.nivelRio.viewmodel.NivelRioViewModel
import com.dflch.dashboardemc.ui.presentation.niveltanque.viewModel.NivelTanqueViewModel
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.TurbiedadTanquesViewModel
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.TurbiedadViewModel
import com.dflch.dashboardemc.ui.theme.DashBoardEMCTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(

) {
    private lateinit var navHostController: NavHostController

    //View Models
    private val networkViewModel: NetworkViewModel by viewModels()
    private val nivelRioViewModel: NivelRioViewModel by viewModels()
    private val turbiedadViewModel: TurbiedadViewModel by viewModels()
    private val turbiedadTanquesViewModel: TurbiedadTanquesViewModel by viewModels()
    private val nivelTanqueViewModel: NivelTanqueViewModel by viewModels()
    //private val ircaViewModel: IrcaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            navHostController = rememberNavController()

            DashBoardEMCTheme {
                ScreenContainer(
                    navHostController,
                    networkViewModel,
                    nivelRioViewModel,
                    turbiedadViewModel,
                    turbiedadTanquesViewModel,
                    nivelTanqueViewModel
                )
            }
        }
    }
}


