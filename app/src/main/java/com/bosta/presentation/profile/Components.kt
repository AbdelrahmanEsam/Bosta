package com.bosta.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.bosta.R
import com.bosta.domain.model.albums.AlbumModel
import com.bosta.domain.model.albums.AlbumsModel
import com.bosta.util.designsystem.ui.theme.Gray50
import com.bosta.util.navigation.Destinations
import com.bosta.util.screen.sdp

@Composable
fun Header(userName: String, address: String, email: String, phone: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.profile),
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(10.sdp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {},
            text = userName,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(10.sdp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {},
            text = address,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {},
            text = email,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {},
            text = phone,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(10.sdp))

    }
}


@Composable
fun AlbumsList(navController: NavController,albums: AlbumsModel) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {

        item {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = stringResource(R.string.my_albums),
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        items(albums.albums) { album ->
            AlbumItem(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight().clickable {
                       navController.navigate(Destinations.AlbumDetails.route+"/${album.title}/${album.id}")
                },albumModel = album)
        }

    }
}

@Composable
fun AlbumItem(modifier: Modifier,albumModel: AlbumModel) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End
    ) {
        Text(
            modifier = Modifier,
            text = albumModel.title,
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.height(3.sdp))
        Divider(color = Gray50, thickness = 1.sdp)
    }
}

