package com.bosta.domain.usecases

import com.bosta.domain.repository.Repository
import com.bosta.util.response.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlbumByUserIdUseCase @Inject constructor(private val repository: Repository) {

    suspend fun <T> execute(userId : String) : Flow<Response<T>>
    {
        return repository.getAlbumByUserId(userId)
    }
}