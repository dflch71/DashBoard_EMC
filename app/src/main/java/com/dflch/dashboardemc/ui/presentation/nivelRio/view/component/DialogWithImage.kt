package com.dflch.dashboardemc.ui.presentation.nivelRio.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dflch.dashboardemc.R

@Composable
fun DialogWithImage(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    fecha: String,
    nivel: String,
    nivelAmarilla: String,
    alertaAmarilla: String,
    nivelNaranja: String,
    alertaNaranja: String,
    nivelRoja: String,
    alertaRoja: String,
) {

    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = "NIVEL RIO",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp),
                )

                Text(
                    text = fecha,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                )

                Text(
                    text = "$nivel m.s.n.m",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 8.dp, bottom = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.yellow),
                        contentDescription = "Descripción de la imagen",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .width(24.dp)
                            .align(alignment = Alignment.CenterVertically)
                    )

                    Text(
                        text = "Amarilla",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 8.dp)
                            .width(65.dp),
                    )

                    Text(
                        text = nivelAmarilla,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .width(50.dp),
                    )

                    Text(
                        text = alertaAmarilla,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .width(70.dp),
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 8.dp, bottom = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.orange),
                        contentDescription = "Descripción de la imagen",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .width(24.dp)
                            .align(alignment = Alignment.CenterVertically),
                    )

                    Text(
                        text = "Naranja",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 8.dp)
                            .width(65.dp),
                    )

                    Text(
                        text = nivelNaranja,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .width(50.dp),
                    )

                    Text(
                        text = alertaNaranja,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .width(70.dp),
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 8.dp, bottom = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.red),
                        contentDescription = "Descripción de la imagen",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .width(24.dp)
                            .align(alignment = Alignment.CenterVertically),
                    )

                    Text(
                        text = "Roja",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 8.dp)
                            .width(65.dp),
                    )

                    Text(
                        text = nivelRoja,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .width(50.dp),
                    )

                    Text(
                        text = alertaRoja,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .width(70.dp),
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(start = 100.dp)
                    ) { Text("") }

                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                    ) { Text("OK") }
                }
            }
        }
    }
}
