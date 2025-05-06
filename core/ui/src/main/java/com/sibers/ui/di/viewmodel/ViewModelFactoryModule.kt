package com.sibers.ui.di.viewmodel

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {
    @Binds
    fun bind(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}