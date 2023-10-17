package com.bosta.data.datasource.remote

import com.bosta.util.response.Response
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    suspend fun <T> getUserById(id : String) : Flow<Response<T>>

    suspend fun <T> getAlbumByUserId(id : String) : Flow<Response<T>>

    suspend fun <T> getPhotosByAlbumId(id : String) : Flow<Response<T>>

}