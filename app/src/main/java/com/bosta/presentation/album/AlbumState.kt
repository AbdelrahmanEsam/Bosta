package com.bosta.presentation.album

import com.bosta.domain.model.photos.PhotoModel
import com.bosta.domain.model.photos.PhotosModel

data class AlbumState(
    val albumName: String = "",
    val albumId: String = "",
    val search: String = "",
    val photosModel: PhotosModel? = null,
    val filteredPhotos: List<PhotoModel> = emptyList()
)
