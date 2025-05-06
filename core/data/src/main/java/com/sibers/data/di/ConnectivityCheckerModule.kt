package com.sibers.data.di

import com.sibers.data.repository.ConnectivityCheckerImpl
import com.sibers.domain.repository.ConnectivityChecker
import dagger.Binds
import dagger.Module

@Module
abstract class ConnectivityCheckerModule {
    @Binds
    abstract fun bindConnectivityChecker(repositoryImpl: ConnectivityCheckerImpl): ConnectivityChecker
}