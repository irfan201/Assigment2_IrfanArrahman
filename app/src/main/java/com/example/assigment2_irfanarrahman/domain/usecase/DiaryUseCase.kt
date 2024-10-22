package com.example.assigment2_irfanarrahman.domain.usecase

import com.example.assigment2_irfanarrahman.data.model.DiaryEntities
import com.example.assigment2_irfanarrahman.data.repository.DiaryRepository
import javax.inject.Inject

class DiaryUseCase @Inject constructor(private val diaryRepository: DiaryRepository) {
    suspend fun getDiary(): List<DiaryEntities> {
        return diaryRepository.getDiary()
    }

    suspend fun getDiaryDate(date: String): List<DiaryEntities> {
        return diaryRepository.getDiaryDate(date)
    }

    suspend fun getDiaryTittle(tittle: String): List<DiaryEntities> {
        return diaryRepository.getDiaryTittle(tittle)
    }

    suspend fun getDiaryDetail(id: Int): DiaryEntities {
        return diaryRepository.getDiaryDetail(id)
    }

    suspend fun insertDiary(diaryEntities: DiaryEntities) {
        diaryRepository.insertDiary(diaryEntities)
    }

    suspend fun deleteDiary(diaryEntities: DiaryEntities) {
        diaryRepository.deleteDiary(diaryEntities)
    }

    suspend fun updateDiary(diaryEntities: DiaryEntities) {
        diaryRepository.updateDiary(diaryEntities)
    }

}