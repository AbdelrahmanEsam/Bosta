package com.bosta.di

import com.bosta.data.datasource.remote.RemoteDataSource
import com.bosta.data.datasource.remote.RemoteDataSourceImpl
import com.bosta.data.repository.RepositoryImpl
import com.bosta.domain.repository.Repository
import com.bosta.domain.usecases.GetAlbumByUserIdUseCase
import com.bosta.domain.usecases.GetPhotosByAlbumIdUseCase
import com.bosta.domain.usecases.GetUserByIdUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
interface ViewModelModule {


    @Binds
    fun provideRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource


    @Binds
    fun provideRepository(repositoryImpl: RepositoryImpl): Repository

    companion object {

        @ViewModelScoped
        @Provides
        fun provideGetUserByIdUseCase(repository: Repository): GetUserByIdUseCase =
            GetUserByIdUseCase(repository)

        @ViewModelScoped
        @Provides
        fun provideGetAlbumByUserIdUseCase(repository: Repository): GetAlbumByUserIdUseCase =
            GetAlbumByUserIdUseCase(repository)

        @ViewModelScoped
        @Provides
        fun provideGetPhotosByAlbumIdUseCase(repository: Repository): GetPhotosByAlbumIdUseCase =
            GetPhotosByAlbumIdUseCase(repository)

    }
}