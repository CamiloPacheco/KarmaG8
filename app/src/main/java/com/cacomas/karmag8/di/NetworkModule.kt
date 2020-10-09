package com.cacomas.karmag8.di

import com.cacomas.karmag8.repository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideAuthRepository() : RegisterRepository {
        val firebaseAuthRepository = RegisterRepository()
        return firebaseAuthRepository
    }
}