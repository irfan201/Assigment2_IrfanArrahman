package com.example.assigment2_irfanarrahman.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DiaryDao {

    @Insert
    suspend fun insertDiary(diaryEntities: DiaryEntities)

    @Query("Select * From diary_table")
    suspend fun getDiary(): List<DiaryEntities>

    @Query("SELECT * FROM diary_table WHERE tittle LIKE '%' || :name || '%'")
    suspend fun getDiaryTittle(name:String):List<DiaryEntities>

    @Query("Select * From diary_table Where date = :date")
    suspend fun getDiaryDate(date:String):List<DiaryEntities>

    @Query("Select * From diary_table Where id = :id")
    suspend fun getDiaryDetail(id:Int): DiaryEntities

    @Delete
    suspend fun deleteDiary(diaryEntities: DiaryEntities)

    @Update
    suspend fun updateDiary(diaryEntities: DiaryEntities)
}