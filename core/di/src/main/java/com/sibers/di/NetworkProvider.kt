package com.sibers.di

import com.sibers.datasource.ItemNetworkSource

interface NetworkProvider {
    fun provideItemNetworkSource(): ItemNetworkSource
}