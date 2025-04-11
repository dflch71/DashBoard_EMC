package com.dflch.dashboardemc.ui.container

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dflch.dashboardemc.ui.presentation.home.view.HomeScreen
import com.dflch.dashboardemc.ui.presentation.irca.view.IrcaScreen
import com.dflch.dashboardemc.ui.presentation.irca.viewmodel.IrcaViewModel
import com.dflch.dashboardemc.ui.presentation.network.viewmodel.NetworkViewModel
import com.dflch.dashboardemc.ui.presentation.nivelRio.view.NivelRioScreen
import com.dflch.dashboardemc.ui.presentation.nivelRio.viewmodel.NivelRioViewModel
import com.dflch.dashboardemc.ui.presentation.niveltanque.view.NivelTanqueScreen
import com.dflch.dashboardemc.ui.presentation.niveltanque.view.NivelTanqueT1P1Screen
import com.dflch.dashboardemc.ui.presentation.niveltanque.view.NivelTanqueT1P2Screen
import com.dflch.dashboardemc.ui.presentation.niveltanque.view.NivelTanqueT2P2Screen
import com.dflch.dashboardemc.ui.presentation.niveltanque.viewModel.NivelTanqueViewModel
import com.dflch.dashboardemc.ui.presentation.turbiedad.view.TurbiedadPlanta01Screen
import com.dflch.dashboardemc.ui.presentation.turbiedad.view.TurbiedadPlanta02Screen
import com.dflch.dashboardemc.ui.presentation.turbiedad.view.TurbiedadScreen
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.TurbiedadTanquesViewModel
import com.dflch.dashboardemc.ui.presentation.turbiedad.viewModel.TurbiedadViewModel

@Composable
fun ScreenContainer(
    navHost: NavHostController,
    networkViewModel: NetworkViewModel,
    nivelRioViewModel: NivelRioViewModel,
    turbiedadViewModel: TurbiedadViewModel,
    turbiedadTanquesViewModel: TurbiedadTanquesViewModel,
    nivelTanqueViewModel: NivelTanqueViewModel,
    ircaViewModel: IrcaViewModel
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
                turbiedadTanquesViewModel,
                nivelTanqueViewModel,
                ircaViewModel,
                navHost
            )
        }

        composable(NavGraph.NivelRio.route) {
            NivelRioScreen(nivelRioViewModel)
        }

        composable(NavGraph.Turbiedad.route) {
            TurbiedadScreen(turbiedadViewModel, turbiedadTanquesViewModel, navHost)
        }

        composable(NavGraph.TurbiedadPlanta01.route) {
            TurbiedadPlanta01Screen(turbiedadViewModel, navHost)
        }

        composable(NavGraph.TurbiedadPlanta02.route) {
            TurbiedadPlanta02Screen(turbiedadViewModel, navHost)
        }

        composable(NavGraph.NivelTanque.route) {
            NivelTanqueScreen(nivelTanqueViewModel, navHost)
        }

        composable(NavGraph.NivelTanqueT1P1.route) {
            NivelTanqueT1P1Screen(nivelTanqueViewModel, navHost)
        }

        composable(NavGraph.NivelTanqueT1P2.route) {
            NivelTanqueT1P2Screen(nivelTanqueViewModel, navHost)
        }

        composable(NavGraph.NivelTanqueT2P2.route) {
            NivelTanqueT2P2Screen(nivelTanqueViewModel, navHost)
        }


        composable(NavGraph.Irca.route) {
            IrcaScreen(ircaViewModel)
        }


    }
}