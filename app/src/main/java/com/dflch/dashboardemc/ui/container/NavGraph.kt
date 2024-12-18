package com.dflch.dashboardemc.ui.container

sealed class NavGraph (val route: String) {
    object Home: NavGraph("home_screen")
    object NivelRio: NavGraph("nivel_rio_screen")
    object TurbiedadColor: NavGraph("turbiedad_color_screen")
    object NivelTanque: NavGraph("nivel_tanque_screen")
    object Irca: NavGraph("irca_screen")
}