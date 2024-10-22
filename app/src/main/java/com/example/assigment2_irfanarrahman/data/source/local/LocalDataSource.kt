package com.example.assigment2_irfanarrahman.data.source.local

import com.example.assigment2_irfanarrahman.data.source.local.room.DiaryDao
import com.example.assigment2_irfanarrahman.data.model.DiaryEntities
import javax.inject.Inject

interface LocalDataSource {
    suspend fun getDiary(): List<DiaryEntities>
    suspend fun getDiaryDate(date: String): List<DiaryEntities>
    suspend fun getDiaryTittle(tittle: String): List<DiaryEntities>
    suspend fun getDiaryDetail(id: Int): DiaryEntities
    suspend fun insertDiary(diaryEntities: DiaryEntities)
    suspend fun deleteDiary(diaryEntities: DiaryEntities)
    suspend fun updateDiary(diaryEntities: DiaryEntities)
}

class LocalDataSourceImpl @Inject constructor(private val diaryDao: DiaryDao) : LocalDataSource {
    override suspend fun getDiary(): List<DiaryEntities> {
        return diaryDao.getDiary()
    }

    override suspend fun getDiaryDate(date: String): List<DiaryEntities> {
        return diaryDao.getDiaryDate(date)
    }

    override suspend fun getDiaryTittle(tittle: String): List<DiaryEntities> {
        return diaryDao.getDiaryTittle(tittle)
    }

    override suspend fun getDiaryDetail(id: Int): DiaryEntities {
       return diaryDao.getDiaryDetail(id)
    }

    override suspend fun insertDiary(diaryEntities: DiaryEntities) {
        diaryDao.insertDiary(diaryEntities)
    }

    override suspend fun deleteDiary(diaryEntities: DiaryEntities) {
        diaryDao.deleteDiary(diaryEntities)
    }

    override suspend fun updateDiary(diaryEntities: DiaryEntities) {
        diaryDao.updateDiary(diaryEntities)
    }

}