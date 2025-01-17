package com.dflch.dashboardemc.ui.presentation.turbiedad.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dflch.dashboardemc.domain.model.nivelrio.LecturasPlantas

@Composable
fun TurbiedadContent(turbiedadP1: List<LecturasPlantas>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(turbiedadP1) { turbiedad ->
            TurbiedadItem(turbiedad)
        }
    }
}

