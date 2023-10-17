package com.bosta.data.datasource.remote

import android.util.Log
import com.bosta.util.response.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class RemoteDataSourceImpl @Inject constructor(private val retrofitInterface: RetrofitInterface) :
    RemoteDataSource {

    override suspend fun <T> getUserById(id: String): Flow<Response<T>> {
        return flow {
            try {
                emit(
                    Response.Success(
                        retrofitInterface.getUserById(id) as T
                    )
                )
            } catch (e: Exception) {
                emit(Response.Failure(e.message ?: "unknown error"))
            }
        }
    }

    override suspend fun <T> getAlbumByUserId(id: String): Flow<Response<T>> {
        return flow {
            try {
                emit(
                    Response.Success(
                        retrofitInterface.getAlbumByUserId(id) as T
                    )
                )
            } catch (e: Exception) {
                emit(Response.Failure(e.message ?: "unknown error"))
            }
        }
    }

    override suspend fun <T> getPhotosByAlbumId(id: String): Flow<Response<T>> {
        return flow {
            try {
                emit(
                    Response.Success(
                        retrofitInterface.getPhotoByAlbumId(id) as T
                    )
                )
            } catch (e: Exception) {
                emit(Response.Failure(e.message ?: "unknown error"))
            }
        }
    }
}