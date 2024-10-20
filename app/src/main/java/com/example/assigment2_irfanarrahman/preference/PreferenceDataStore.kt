package com.example.assigment2_irfanarrahman.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

private const val USER_PREFERENCE = "user_preference"
val Context.dataStore:DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCE)
class PreferenceDataStore private constructor(private val dataStore: DataStore<Preferences>){

    suspend fun puLogin(isLogin:Boolean){
        dataStore.edit { preference ->
            preference[DataStoreConstant.ISLOGIN] = isLogin
        }
    }
    fun getLogin():Boolean?{
        return runBlocking(Dispatchers.IO) {
            dataStore.data.first()[DataStoreConstant.ISLOGIN]
        }
    }
    suspend fun putEmail(email :String){
        dataStore.edit { preference ->
            preference[DataStoreConstant.EMAIL] = email
        }
    }

    fun getEmail():String?{
        return runBlocking(Dispatchers.IO) {
            dataStore.data.first()[DataStoreConstant.EMAIL]
        }
    }
    companion object{
        @Volatile
        private var INSTANCE: PreferenceDataStore? = null
        fun getInstance(dataStore: DataStore<Preferences>): PreferenceDataStore{
            return INSTANCE ?: synchronized(this){
                val instance = PreferenceDataStore(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}