package com.fitri.jilbab.di

import com.fitri.jilbab.data.remote.ApiClient
import com.fitri.jilbab.data.remote.ApiServices
import com.fitri.jilbab.data.remote.AuthInterceptor
import com.fitri.jilbab.data.remote.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(
        okHttpClient: OkHttpClient
    ): ApiServices {
        return ApiClient(okHttpClient).instance()
    }

    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient = okHttpClient(authInterceptor)
}