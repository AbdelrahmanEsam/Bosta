package com.bosta.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.bosta.R
import com.bosta.presentation.album.AlbumScreen
import com.bosta.presentation.profile.ProfileScreen
import com.bosta.util.navigation.Destinations
import com.wagbat.utils.connectivity.ConnectivityObserver
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BostaNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    connectivityStatus: StateFlow<ConnectivityObserver.Status>,
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Profile.route,
        modifier = modifier,
    ) {


        composable(route = Destinations.Profile.route)
        {
            ProfileScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(20.dp),
                navController = navController,
                connectivityStatus = connectivityStatus
            )
        }


        composable(route = Destinations.AlbumDetails.route)
        {

        }


        composable(
            route = Destinations.AlbumDetails.route + "/{albumName}/{albumId}",
            arguments = listOf(navArgument("albumName") { type = NavType.StringType },
                navArgument("albumId") { type = NavType.StringType }
            ),
            deepLinks = listOf(navDeepLink {
                uriPattern = Destinations.AlbumDetails.route + "/{albumName}/{albumId}"
            }),
        ) {
            val albumName = it.arguments?.getString(stringResource(R.string.albumName))
            val albumId = it.arguments?.getString(stringResource(R.string.albumid))
            AlbumScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(20.dp),
                navController = navController,
                connectivityStatus = connectivityStatus,
                albumName = albumName,
                albumId = albumId
            )
        }

    }


}