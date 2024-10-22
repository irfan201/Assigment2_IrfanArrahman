package com.example.assigment2_irfanarrahman.domain.model

import com.example.assigment2_irfanarrahman.data.model.DiaryEntities

sealed class DiaryState {
    object Loading: DiaryState()
    data class Success(val diary:List<DiaryEntities>):DiaryState()
    data class Error(val message:String):DiaryState()
}