package com.dflch.dashboardemc.ui.presentation.irca.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Science
import androidx.compose.material.icons.twotone.WaterDrop
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dflch.dashboardemc.domain.model.irca.DataIrca
import com.dflch.dashboardemc.ui.presentation.irca.viewmodel.IrcaViewModel
import com.dflch.dashboardemc.ui.presentation.irca.viewmodel.UiState
import kotlinx.coroutines.delay


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun IrcaScreen(
    ircaViewModel: IrcaViewModel
) {
    val uiState by ircaViewModel.uiState.collectAsState()
    var isErrorMsg by remember { mutableStateOf(false) }
    var msgError by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            //ircaViewModel.fetchIrca("2025-03-01", "2025-03-31")
            delay(300000L) // Actualiza cada 5 minutos
        }
    }


    when(uiState) {
        is UiState.Error -> {
            msgError = (uiState as UiState.Error).message
            isErrorMsg = true
        }

        UiState.Loading -> {
            Box(modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success<*> -> {
            val data = (uiState as UiState.Success<List<DataIrca>>).data
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "INDICADORES IRCA E.M.C",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        items(data.size) { index ->
                            Spacer(modifier = Modifier.height(8.dp))
                            CardIrca(dataIrca = data[index])
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardIrca(dataIrca: DataIrca)
{
    OutlinedCard(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface,),
        border = BorderStroke(0.5.dp, Color.LightGray),
        modifier = Modifier.height(160.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ){
            Row() {

                Column(
                    modifier = Modifier.weight(0.8f)
                ) {
                    Text(
                        text = "${dataIrca.month} ${dataIrca.year} ",
                        fontWeight = FontWeight.Light,
                        fontSize = 20.sp,
                        color = Color.Black,
                        maxLines = 1,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp)
                    )
                    Text(
                        text = "${dataIrca.startDate} - ${dataIrca.endDate}",
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                    )
                }

                if (dataIrca.irca.length > 1) {
                    Icon(
                        imageVector = Icons.TwoTone.WaterDrop,
                        contentDescription = "Icono de laboratorio",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .weight(0.2f)
                            .size(36.dp)
                            .padding(start = 16.dp)
                            .align(Alignment.CenterVertically),

                        )
                }
            }


            Spacer(modifier = Modifier.size(4.dp))
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFFEDE7F6))
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(0.45f)
                        .height(100.dp)
                        .background(Color(0xFFD3D7EF), shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "IRCA",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                        Text(dataIrca.irca.take(6), fontSize = 36.sp, fontWeight = FontWeight.Light, maxLines = 1)
                    }
                }

                // Sum
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(0.55f)
                        .height(100.dp)
                        .background(Color(0xFFD3D7EF), shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Σ IRCA: ${dataIrca.sumIRCA.take(8)}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.DarkGray,
                            maxLines = 1
                        )
                        Text(
                            "Σ Muestras: ${dataIrca.samplesCount}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.DarkGray,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

