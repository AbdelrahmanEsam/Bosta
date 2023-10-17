package com.bosta.presentation.album

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bosta.R
import com.bosta.util.screen.sdp


@Composable
fun Header(
    modifier: Modifier,
    albumName: String,
    searchState: String,
    searchCallback: (String) -> Unit
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = albumName,
        style = MaterialTheme.typography.displayLarge,
        textAlign = TextAlign.End
    )
    Spacer(modifier = Modifier.height(10.sdp))
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(
                    topStart = 5.dp,
                    topEnd = 5.dp,
                    bottomStart = 5.dp,
                    bottomEnd = 5.dp
                )
            ),
        value = searchState,
        onValueChange = {
            searchCallback(it)
        },
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp), verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                Spacer(modifier = Modifier.size(15.dp))
                Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                    innerTextField()
                    if (searchState.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search_in_images),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.End
                        )
                    }
                }
                Spacer(modifier = Modifier.size(15.dp))
            }
        },
    )
}