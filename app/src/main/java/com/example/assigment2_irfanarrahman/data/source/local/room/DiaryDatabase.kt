package com.example.assigment2_irfanarrahman.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assigment2_irfanarrahman.data.model.DiaryEntities


@Database([DiaryEntities::class], version = 1, exportSchema = false)
abstract class DiaryDatabase: RoomDatabase() {
    abstract fun diaryDao() : DiaryDao

    companion object{
        @Volatile
        private var INSTANCE: DiaryDatabase? = null

        fun getDatabase(context: Context): DiaryDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext, DiaryDatabase::class.java,
                    "diary_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}