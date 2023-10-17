package com.bosta.presentation.album

sealed interface AlbumIntents{
    data object GetAlbumPhotos : AlbumIntents
    data class UpdateAlbumId(val id : String) : AlbumIntents
    data class UpdateAlbumName(val name : String) : AlbumIntents
    data class UpdateSearch(val search : String) : AlbumIntents

}