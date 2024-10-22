package com.example.assigment2_irfanarrahman.domain.model

import com.example.assigment2_irfanarrahman.data.model.DiaryEntities

sealed class DiaryStateDetail {
    object Loading : DiaryStateDetail()
    data class Success(val diary: DiaryEntities) : DiaryStateDetail()
    data class Error(val message: String) : DiaryStateDetail()

}