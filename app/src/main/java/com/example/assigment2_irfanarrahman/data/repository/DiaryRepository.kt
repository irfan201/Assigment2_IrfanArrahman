package com.example.assigment2_irfanarrahman.data.repository

import com.example.assigment2_irfanarrahman.data.source.local.LocalDataSource
import com.example.assigment2_irfanarrahman.data.model.DiaryEntities
import javax.inject.Inject

interface DiaryRepository {
    suspend fun getDiary(): List<DiaryEntities>
    suspend fun getDiaryDate(date: String): List<DiaryEntities>
    suspend fun getDiaryTittle(tittle: String): List<DiaryEntities>
    suspend fun getDiaryDetail(id: Int): DiaryEntities
    suspend fun insertDiary(diaryEntities: DiaryEntities)
    suspend fun deleteDiary(diaryEntities: DiaryEntities)
    suspend fun updateDiary(diaryEntities: DiaryEntities)

}

class DiaryRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource):DiaryRepository{
    override suspend fun getDiary(): List<DiaryEntities> {
        return localDataSource.getDiary()
    }

    override suspend fun getDiaryDate(date: String): List<DiaryEntities> {
        return localDataSource.getDiaryDate(date)
    }

    override suspend fun getDiaryTittle(tittle: String): List<DiaryEntities> {
        return localDataSource.getDiaryTittle(tittle)
    }

    override suspend fun getDiaryDetail(id: Int): DiaryEntities {
        return localDataSource.getDiaryDetail(id)
    }

    override suspend fun insertDiary(diaryEntities: DiaryEntities) {
        localDataSource.insertDiary(diaryEntities)
    }

    override suspend fun deleteDiary(diaryEntities: DiaryEntities) {
        localDataSource.deleteDiary(diaryEntities)
    }

    override suspend fun updateDiary(diaryEntities: DiaryEntities) {
        localDataSource.updateDiary(diaryEntities)
    }

}

