package com.bosta.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import com.bosta.di.DispatcherAnnotations.*


@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
        @Provides
        @Dispatcher(IO)
        fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

        @Provides
        @Dispatcher(Default)
        fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

        @Provides
        @Dispatcher(Main)
        fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

        @Provides
        @Dispatcher(Unconfined)
        fun providesUnconfinedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined

}