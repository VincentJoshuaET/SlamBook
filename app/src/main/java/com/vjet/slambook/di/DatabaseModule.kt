package com.vjet.slambook.di

import android.content.Context
import androidx.room.Room
import com.vjet.slambook.database.ItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideItemDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ItemDatabase::class.java, "database")
            .build()

    @Provides
    @Singleton
    fun provideItemDao(database: ItemDatabase) = database.itemDao()

}