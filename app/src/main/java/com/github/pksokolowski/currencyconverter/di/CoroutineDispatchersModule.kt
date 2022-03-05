package com.github.pksokolowski.currencyconverter.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoroutineDispatchersModule {

    @Singleton
    @Provides
    @IoDispatcher
    fun providesIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Singleton
    @Provides
    @SharedComputationDispatcher
    fun provideSharedComputationDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Singleton
    @Provides
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SharedComputationDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher