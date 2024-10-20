package com.example.assigment2_irfanarrahman.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("diary_table")
data class DiaryEntities (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val tittle:String,
    val note:String,
    val date:String,
    val expressionImage:Int

)