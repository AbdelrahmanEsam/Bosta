package com.bosta.data.repository

import com.bosta.data.datasource.remote.RemoteDataSource
import com.bosta.data.dto.albums.AlbumsDto
import com.bosta.data.dto.photos.PhotosDto
import com.bosta.data.dto.profiles.UserDto
import com.bosta.data.mappers.toAlbumModel
import com.bosta.data.mappers.toAlbumsModel
import com.bosta.data.mappers.toPhotosModel
import com.bosta.data.mappers.toUserModel
import com.bosta.domain.repository.Repository
import com.bosta.util.response.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class RepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    Repository {
    override suspend fun <T> getUserById(id: String): Flow<Response<T>> {
        return try {
            remoteDataSource.getUserById<UserDto>(id).map { response ->
                Response.Success(response.data?.toUserModel() as T)
            }
        } catch (e: Exception) {
            flowOf(Response.Failure(e.message ?: "unkown error"))
        }
    }

    override suspend fun <T> getAlbumByUserId(id: String): Flow<Response<T>> {
        return try {
            remoteDataSource.getAlbumByUserId<AlbumsDto>(id).map { response ->
                Response.Success(response.data?.toAlbumsModel() as T)
            }
        } catch (e: Exception) {
            flowOf(Response.Failure(e.message ?: "unkown error"))
        }
    }

    override suspend fun <T> getPhotosByAlbumId(id: String): Flow<Response<T>> {
        return try {
            remoteDataSource.getPhotosByAlbumId<PhotosDto>(id).map { response ->
                Response.Success(response.data?.toPhotosModel() as T)
            }
        } catch (e: Exception) {
            flowOf(Response.Failure(e.message ?: "unkown error"))
        }
    }
}