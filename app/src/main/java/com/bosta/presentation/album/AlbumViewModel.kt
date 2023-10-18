package com.bosta.presentation.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bosta.di.Dispatcher
import com.bosta.di.DispatcherAnnotations
import com.bosta.domain.model.photos.PhotosModel
import com.bosta.domain.usecases.GetPhotosByAlbumIdUseCase
import com.bosta.util.response.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    @Dispatcher(DispatcherAnnotations.IO) private val ioDispatcher: CoroutineDispatcher,
    private val getPhotosByAlbumIdUseCase: GetPhotosByAlbumIdUseCase,
) : ViewModel() {


    private val _state: MutableStateFlow<AlbumState> = MutableStateFlow(AlbumState())
    val state = _state.asStateFlow()

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error = _error.asSharedFlow()


    fun onEvent(intent: AlbumIntents) {
        when (intent) {
            is AlbumIntents.GetAlbumPhotos -> {
                getAlbumPhotos(_state.value.albumId)
            }

            is AlbumIntents.UpdateAlbumId -> {
                _state.update { it.copy(albumId = intent.id) }
            }

            is AlbumIntents.UpdateAlbumName -> {
                _state.update { it.copy(albumName = intent.name) }
            }

            is AlbumIntents.UpdateSearch -> {
                _state.update { albumState ->
                    albumState.copy(search = intent.search,
                        filteredPhotos = albumState.photosModel?.photos?.filter {
                            it.title.lowercase().contains(intent.search)
                        } ?: emptyList()
                    )
                }
            }
        }
    }


    private fun getAlbumPhotos(id: String) {
        viewModelScope.launch(ioDispatcher) {
            getPhotosByAlbumIdUseCase.execute<PhotosModel>(id).collectLatest { response ->
                when (response) {
                    is Response.Failure -> {

                    }
                    is Response.Success -> {
                        _state.update { albumState ->

                            albumState.copy(
                                photosModel = response.data,
                                filteredPhotos = response.data?.photos?.filter {
                                    it.title.lowercase().contains(albumState.search.lowercase())
                                } ?: emptyList())
                        }
                    }
                }

            }

        }
    }


}