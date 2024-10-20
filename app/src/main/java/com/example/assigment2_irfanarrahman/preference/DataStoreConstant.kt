package com.example.assigment2_irfanarrahman.preference

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreConstant {
    val ISLOGIN = booleanPreferencesKey("isLogin")
    val EMAIL = stringPreferencesKey("email")
}