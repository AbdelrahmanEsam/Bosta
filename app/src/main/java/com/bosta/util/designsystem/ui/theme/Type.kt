package com.bosta.util.designsystem.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bosta.R
import com.bosta.util.screen.textSdp

val kaff = FontFamily(
    Font(R.font.kaff_meduim,FontWeight.Medium),
    Font(R.font.kaff_light,FontWeight.Light)
)

// Set of Material typography styles to start with
val Typography = Typography(


    displayLarge = TextStyle(
        fontFamily = kaff,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp
    ),

    displayMedium = TextStyle(
        fontFamily = kaff,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),

    headlineMedium = TextStyle(
        fontFamily = kaff,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = kaff,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),

    bodySmall = TextStyle(
        fontFamily = kaff,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    ),
)

