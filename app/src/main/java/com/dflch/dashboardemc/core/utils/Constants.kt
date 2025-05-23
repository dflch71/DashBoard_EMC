package com.dflch.dashboardemc.core.utils

object Constants {
    //CONSULTAR NIVEL HISTÓRICO DIA
    //historicoLecturas(tipo: Integer; car_id: Integer; lug_id: Integer; smt_id: Integer)
    //Tipo
    //1 .. Dia
    //2 .. Semana-Mes
    //3 .. Mes
    //http://181.204.95.204:8070/datasnap/rest/TServerMethods/historicoLecturas/1/44/23/3 Nivel Rio
    //http://181.204.95.204:8070/datasnap/rest/TServerMethods/historicoLecturas/1/25/1/1 Turbiedad P1 AGUA CRUDA
    //http://181.204.95.204:8070/datasnap/rest/TServerMethods/historicoLecturas/1/25/1/2 Turbiedad P2 AGUA CRUDA
    //http://181.204.95.204:8070/datasnap/rest/TServerMethods/historicoLecturas/1/3/6/1 Turbiedad P1 TANQUES
    //http://181.204.95.204:8070/datasnap/rest/TServerMethods/historicoLecturas/1/3/6/2 Turbiedad P2 TANQUES
    //http://181.204.95.204:8070/datasnap/rest/TServerMethods/historicoLecturas/1/33/9/1 Nivel Tanque 1 P1
    //http://181.204.95.204:8070/datasnap/rest/TServerMethods/historicoLecturas/1/41/9/2 Nivel Tanque 1 P2
    //http://181.204.95.204:8070/datasnap/rest/TServerMethods/historicoLecturas/1/40/9/2 Nivel Tanque 2 P2

    var IP      = "181.204.95.204"
    var PUERTO  = "8070"
    var MEDICIONES_URL = "http://$IP:$PUERTO/datasnap/rest/TServerMethods/"
    const val GET_PATH_HISTORICO_NIVEL_RIO = "historicoLecturas/1/44/23/3"
    const val GET_PATH_AGUA_CRUDA_TURBIEDAD_P1 = "historicoLecturas/1/25/1/1"
    const val GET_PATH_AGUA_CRUDA_TURBIEDAD_P2 = "historicoLecturas/1/25/1/2"
    const val GET_PATH_TANQUES_TURBIEDAD_P1 = "historicoLecturas/1/3/6/1"
    const val GET_PATH_TANQUES_TURBIEDAD_P2 = "historicoLecturas/1/3/6/2"
    const val GET_PATH_AGUA_TRATADA_NIVEL_TANQUE1_P1 = "historicoLecturas/1/33/9/1"
    const val GET_PATH_AGUA_TRATADA_NIVEL_TANQUE1_P2 = "historicoLecturas/1/41/9/2"
    const val GET_PATH_AGUA_TRATADA_NIVEL_TANQUE2_P2 = "historicoLecturas/1/40/9/2"

    //IRCA
    //https://intranet.emcartago.com/api/lab/Samples/datairca?InitialDate=2025-02-01&EndDate=2025-02-28
    var MEDICIONES_IRCA = "https://intranet.emcartago.com/api/lab/Samples/"
    //const val GET_PATH_IRCA = "datairca?InitialDate=FechaIni&EndDate=FechaFin"
    const val GET_PATH_IRCA = "datairca?"

}