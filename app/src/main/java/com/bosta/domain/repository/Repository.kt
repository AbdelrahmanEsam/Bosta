package com.bosta.domain.repository

import com.bosta.util.response.Response
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun <T> getUserById(id : String) : Flow<Response<T>>

    suspend fun <T> getAlbumByUserId(id : String) : Flow<Response<T>>

    suspend fun <T> getPhotosByAlbumId(id : String) : Flow<Response<T>>
}