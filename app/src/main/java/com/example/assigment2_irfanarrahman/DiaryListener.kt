package com.example.assigment2_irfanarrahman

import com.example.assigment2_irfanarrahman.room.DiaryEntities

interface DiaryListener {
    fun onDelete(diaryEntities: DiaryEntities)
    fun onClick(id: Int)
}