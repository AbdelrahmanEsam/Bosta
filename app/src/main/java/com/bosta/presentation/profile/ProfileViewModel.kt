package com.bosta.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bosta.di.Dispatcher
import com.bosta.di.DispatcherAnnotations.IO
import com.bosta.domain.model.albums.AlbumsModel
import com.bosta.domain.model.profiles.UserModel
import com.bosta.domain.usecases.GetAlbumByUserIdUseCase
import com.bosta.domain.usecases.GetUserByIdUseCase
import com.bosta.util.response.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getAlbumByUserIdUseCase: GetAlbumByUserIdUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error = _error.asSharedFlow()


    fun onEvent(intent: ProfileIntents) {
        when (intent) {
            ProfileIntents.GetUserProfileAndAlbumsData -> {
                getUserProfileAndAlbum()
            }
        }


    }

    private fun getUserProfileAndAlbum() {
        viewModelScope.launch(ioDispatcher) {
            val randomId = Random.nextInt(1, 11)
            _state.update { it.copy(userId = randomId) }
            val getUserProfileData = async { getUserById() }
            val getUserAlbumsData = async { getAlbumByUserId() }
            awaitAll(getUserAlbumsData, getUserProfileData)
        }

    }


    private suspend fun getUserById() {
        val randomId = Random.nextInt(1, 11)
        _state.update { it.copy(userId = randomId) }
        getUserByIdUseCase.execute<UserModel>(_state.value.userId.toString())
            .collectLatest { response ->

                when (response) {
                    is Response.Failure -> {
                        _error.emit(response.error.toString())
                    }

                    is Response.Success -> {
                        with(response.data) {
                            _state.update {
                                it.copy(
                                    userName = this?.name ?: "",
                                    email = this?.email ?: "",
                                    phone = this?.phone ?: "",
                                    address = this?.address?.city + this?.address?.street,
                                )
                            }
                        }
                    }


                }
            }
    }


    private suspend fun getAlbumByUserId() {

        getAlbumByUserIdUseCase.execute<AlbumsModel>(_state.value.userId.toString())
            .collectLatest { response ->

                when (response) {
                    is Response.Failure -> {
                        _error.emit(response.error.toString())
                    }

                    is Response.Success -> {
                        with(response.data) {
                            _state.update {
                                it.copy(
                                    albums = this
                                )
                            }
                        }
                    }


                }
            }
    }


}