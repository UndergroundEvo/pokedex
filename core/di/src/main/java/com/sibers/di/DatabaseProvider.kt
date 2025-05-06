package com.sibers.di

import com.sibers.datasource.ItemDatabaseSource

interface DatabaseProvider {
    fun provideItemDatabaseSource(): ItemDatabaseSource
}