package com.vishal.tophiretask.functions

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vishal.tophiretask.model.NewUser
import java.lang.reflect.Type

object SharedPref {
    fun setObject(sharedEditor: SharedPreferences.Editor, key: String, urlList: Any?) {
        val jsonList = Gson().toJson(urlList)
        set(sharedEditor, key, jsonList)
    }

    fun set(sharedEditor: SharedPreferences.Editor, key: String, urlList: String) {
        sharedEditor.putString(key, urlList)
        sharedEditor.apply()
    }

    fun getObject(sharedPref: SharedPreferences, key: String): NewUser? {
        val serializedurlList = sharedPref.getString(key, null)
        if (serializedurlList != null) {
            val type: Type = object : TypeToken<NewUser>() {}.type
            return Gson().fromJson(serializedurlList, type)
        }
        return null
    }
}