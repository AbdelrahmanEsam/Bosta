package com.bosta.data.datasource.remote

import com.bosta.data.dto.albums.AlbumDto
import com.bosta.data.dto.albums.AlbumsDto
import com.bosta.data.dto.photos.PhotoDto
import com.bosta.data.dto.photos.PhotosDto
import com.bosta.data.dto.profiles.UserDto
import com.bosta.domain.model.profiles.UserModel
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path


interface RetrofitInterface {
    @GET("/users/{UserId}")
    suspend fun getUserById(@Path("UserId") userId: String): UserDto

    @GET("/albums")
    suspend fun getAlbumByUserId(@Query("userId") userId: String): AlbumsDto

    @GET("/photos")
    suspend fun getPhotoByAlbumId(@Query("albumId") albumId: String): PhotosDto
}