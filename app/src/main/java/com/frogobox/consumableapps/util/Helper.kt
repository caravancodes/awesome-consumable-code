package com.frogobox.consumableapps.util

import android.content.Context
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

/*
 * Created by Faisal Amir
 * =========================================
 * ConsumableApps
 * Copyright (C) 27/04/2020.      
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * -----------------------------------------
 * FrogoBox Inc
 * com.frogobox.consumableapps.util
 * 
 */
object Helper {

    fun getJsonAssets(context: Context, filename: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(filename).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun <T> dataToJson(model: T) : String? {
        return Gson().toJson(model)
    }

    inline fun <reified T> dataFromJson(word: String?) : T {
        return Gson().fromJson<T>(word, T::class.java)
    }

}