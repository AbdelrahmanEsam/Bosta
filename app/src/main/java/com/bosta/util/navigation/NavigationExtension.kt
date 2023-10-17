package com.bosta.util.navigation

import androidx.navigation.NavHostController
import com.bosta.util.navigation.Destinations

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.navigateAndDeleteBackStack(route: String) =
    this.navigate(route){
        popUpTo(0) {
            inclusive = true
        }
        launchSingleTop = true
    }

fun NavHostController.navigateAndPopUpTo(route: String,popUpTo : String) =
    this.navigate(route){
        popUpTo(popUpTo)
    }
