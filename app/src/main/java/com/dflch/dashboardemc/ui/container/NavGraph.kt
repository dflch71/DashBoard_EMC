package com.dflch.dashboardemc.ui.container

sealed class NavGraph (val route: String) {
    object Home: NavGraph("home_screen")
    object NivelRio: NavGraph("nivel_rio_screen")
    object Turbiedad: NavGraph("turbiedad_screen")
    object TurbiedadPlanta01: NavGraph("turbiedad_planta01_screen")
    object TurbiedadPlanta02: NavGraph("turbiedad_planta02_screen")
    object NivelTanque: NavGraph("nivel_tanque_screen")
    object NivelTanqueT1P1: NavGraph("nivel_tanque_T1P1_screen")
    object NivelTanqueT1P2: NavGraph("nivel_tanque_T1P2_screen")
    object NivelTanqueT2P2: NavGraph("nivel_tanque_T2P2_screen")
    object Irca: NavGraph("irca_screen")
}