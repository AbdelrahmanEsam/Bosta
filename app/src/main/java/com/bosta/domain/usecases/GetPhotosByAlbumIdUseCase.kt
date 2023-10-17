package com.bosta.domain.usecases

import com.bosta.domain.repository.Repository
import com.bosta.util.response.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotosByAlbumIdUseCase @Inject constructor(private val repository: Repository) {

    suspend fun <T> execute(albumId : String) : Flow<Response<T>>
    {
        return repository.getPhotosByAlbumId(albumId)
    }
}