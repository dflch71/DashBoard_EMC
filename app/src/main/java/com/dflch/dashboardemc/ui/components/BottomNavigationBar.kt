package com.dflch.dashboardemc.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.dflch.dashboardemc.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    onItemselected: (selectedIndex: Int) -> Unit
){

    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    val navigationList = listOf(
        NavigationItem(
            title = "Nivel Rio",
            selectedIcon = Icons.Filled.LocationOn,
            unselectedIcon = Icons.Outlined.LocationOn
        ),

        NavigationItem(
            title = "Turbiedad",
            selectedIcon = ImageVector.vectorResource(R.drawable.ic_water_filled),
            unselectedIcon = ImageVector.vectorResource(R.drawable.ic_water_out),
        ),

        NavigationItem(
            title = "Tanques",
            selectedIcon = ImageVector.vectorResource(R.drawable.ic_shower_filled),
            unselectedIcon = ImageVector.vectorResource(R.drawable.ic_shower_outline),
        ),

        NavigationItem(
            title = "IRCA",
            selectedIcon = Icons.Filled.Notifications,
            unselectedIcon = Icons.Outlined.Notifications
        )

    )

    NavigationBar {
        navigationList.forEachIndexed{ index, navigationItem ->
            NavigationBarItem(
                label = {
                    Text(text = navigationItem.title)
                },
                selected = index == selectedItemIndex,
                onClick = {
                    selectedItemIndex = index
                    onItemselected(selectedItemIndex)
                },
                icon = {
                    Icon(
                        imageVector = if(index == selectedItemIndex) navigationItem.selectedIcon else navigationItem.unselectedIcon,
                        contentDescription = navigationItem.title
                    )
                }
            )
        }
    }

}

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)