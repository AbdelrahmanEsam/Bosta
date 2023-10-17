package com.bosta.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bosta.util.collectAsSharedFlowWithLifeCycle
import com.wagbat.utils.connectivity.ConnectivityObserver
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ProfileScreen(
    modifier: Modifier,
    navController: NavHostController,
    connectivityStatus: StateFlow<ConnectivityObserver.Status>,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val connectionStatus = connectivityStatus.collectAsStateWithLifecycle()
    val context = LocalContext.current

    viewModel.error.collectAsSharedFlowWithLifeCycle {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }

    Column(modifier, verticalArrangement = Arrangement.Top) {
        if (connectionStatus.value != ConnectivityObserver.Status.Available && state.value.userName.isEmpty()) {
            //todo
        }else{
            with(state.value) {
                Header(userName = userName, address = address, email = email, phone = phone)
                albums?.let {
                    AlbumsList(albums = it, navController = navController)
                }
            }
        }
    }
}

