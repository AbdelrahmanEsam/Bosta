package com.bosta.di

import android.content.Context
import com.bosta.BuildConfig
import com.bosta.data.datasource.remote.RetrofitInterface
import com.wagbat.utils.connectivity.ConnectivityObserver
import com.bosta.util.connectivity.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideConnectivityObserver(@ApplicationContext context: Context): ConnectivityObserver =
        NetworkConnectivityObserver(context = context)

    @Singleton
    @Provides
    fun providesRetrofitApi(): RetrofitInterface
            = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE).addConverterFactory(
            GsonConverterFactory.create()).build()
        .create(RetrofitInterface::class.java)

}