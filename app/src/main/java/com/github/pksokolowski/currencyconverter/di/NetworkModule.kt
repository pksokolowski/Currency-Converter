package com.github.pksokolowski.currencyconverter.di

import com.github.pksokolowski.currencyconverter.backend.BackendApi
import com.github.pksokolowski.currencyconverter.backend.server.BackendApiImpl
import com.github.pksokolowski.currencyconverter.backend.server.repository.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    fun provideBackendApiMock(userDataRepository: UserDataRepository): BackendApi =
        BackendApiImpl(userDataRepository)
}