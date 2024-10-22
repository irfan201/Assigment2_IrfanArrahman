package com.example.assigment2_irfanarrahman.presenter.adapter

import com.example.assigment2_irfanarrahman.data.model.DiaryEntities

interface DiaryListener {
    fun onDelete(diaryEntities: DiaryEntities)
    fun onClick(id: Int)
}