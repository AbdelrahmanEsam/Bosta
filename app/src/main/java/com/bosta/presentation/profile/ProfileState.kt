package com.bosta.presentation.profile

import com.bosta.domain.model.albums.AlbumsModel

data class ProfileState(
    val userId : Int = 1 ,
    val userName: String = "",
    val address: String= "",
    val email: String= "",
    val phone: String= "",
    val albums: AlbumsModel? = null,
)