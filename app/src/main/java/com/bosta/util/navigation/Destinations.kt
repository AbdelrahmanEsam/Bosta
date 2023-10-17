package com.bosta.util.navigation

sealed class Destinations(val route : String) {
    data object Profile :  Destinations("ProfileScreen")
    data object AlbumDetails :  Destinations("AlbumDetailsScreen")
    data object ImageScreen :  Destinations("ImageScreen")
}