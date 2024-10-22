package com.example.assigment2_irfanarrahman.di

import android.app.Application
import com.example.assigment2_irfanarrahman.data.repository.DiaryRepository
import com.example.assigment2_irfanarrahman.data.repository.DiaryRepositoryImpl
import com.example.assigment2_irfanarrahman.data.source.local.LocalDataSource
import com.example.assigment2_irfanarrahman.data.source.local.LocalDataSourceImpl
import com.example.assigment2_irfanarrahman.data.source.local.room.DiaryDao
import com.example.assigment2_irfanarrahman.data.source.local.room.DiaryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDiaryDatabase(application: Application): DiaryDatabase {
        return DiaryDatabase.getDatabase(application)
    }

    @Provides
    @Singleton
    fun provideDiaryDao(diaryDatabase: DiaryDatabase) = diaryDatabase.diaryDao()


    @Provides
    @Singleton
    fun provideLocalDataSource(diaryDao: DiaryDao) : LocalDataSource{
        return LocalDataSourceImpl(diaryDao)
    }

    @Provides
    @Singleton
    fun provideDiaryRepository(localDataSource: LocalDataSource): DiaryRepository{
        return DiaryRepositoryImpl(localDataSource)
    }


}