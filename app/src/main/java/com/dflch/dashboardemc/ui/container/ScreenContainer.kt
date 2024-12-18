package com.dflch.dashboardemc.ui.container

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.dflch.dashboardemc.ui.presentation.niveltanque.view.NivelTanqueScreen
import com.dflch.dashboardemc.ui.presentation.home.view.HomeScreen
import com.dflch.dashboardemc.ui.presentation.irca.view.IrcaScreen
import com.dflch.dashboardemc.ui.presentation.network.viewmodel.NetworkViewModel
import com.dflch.dashboardemc.ui.presentation.nivelRio.view.NivelRioScreen
import com.dflch.dashboardemc.ui.presentation.nivelRio.viewmodel.NivelRioViewModel
import com.dflch.dashboardemc.ui.presentation.niveltanque.viewModel.NivelTanqueViewModel
import com.dflch.dashboardemc.ui.presentation.turbiedad.view.TurbiedadScreen
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.TurbiedadViewModel

@Composable
fun ScreenContainer(
    navHost: NavHostController,
    networkViewModel: NetworkViewModel,
    nivelRioViewModel: NivelRioViewModel,
    turbiedadViewModel: TurbiedadViewModel,
    nivelTanqueViewModel: NivelTanqueViewModel
){

    NavHost(
        navController = navHost,
        startDestination = NavGraph.Home.route
    ) {
        composable(NavGraph.Home.route) {
            HomeScreen(
                networkViewModel,
                nivelRioViewModel,
                turbiedadViewModel,
                nivelTanqueViewModel
            )
        }

        composable(NavGraph.NivelRio.route) {
            NivelRioScreen(nivelRioViewModel)
        }

        composable(NavGraph.TurbiedadColor.route) {
            TurbiedadScreen(turbiedadViewModel)
        }

        composable(NavGraph.NivelTanque.route) {
            NivelTanqueScreen(nivelTanqueViewModel)
        }

        composable(NavGraph.Irca.route) {
            IrcaScreen()
        }


    }
}