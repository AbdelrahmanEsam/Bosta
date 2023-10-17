package com.bosta.data.mappers

import com.bosta.data.dto.albums.AlbumDto
import com.bosta.data.dto.albums.AlbumsDto
import com.bosta.domain.model.albums.AlbumModel
import com.bosta.domain.model.albums.AlbumsModel


fun AlbumsDto.toAlbumsModel(): AlbumsModel {
    return AlbumsModel(albums = this.map { it.toAlbumModel() })
}

fun AlbumDto.toAlbumModel(): AlbumModel {
    return AlbumModel(id = id ?: 0, title = title ?: "", userId = userId ?: 1)
}