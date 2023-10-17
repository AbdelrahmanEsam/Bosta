package com.bosta.data.mappers

import com.bosta.data.dto.photos.PhotoDto
import com.bosta.data.dto.photos.PhotosDto
import com.bosta.domain.model.photos.PhotoModel
import com.bosta.domain.model.photos.PhotosModel


fun PhotosDto.toPhotosModel(): PhotosModel {
    return PhotosModel(photos = this.map { it.toPhotoModel() })
}

fun PhotoDto.toPhotoModel(): PhotoModel {
    return PhotoModel(
        id = id ?: 0,
        albumId = albumId ?: 1,
        thumbnailUrl = thumbnailUrl ?: "",
        title = title ?: "",
        url = url ?: ""
    )
}