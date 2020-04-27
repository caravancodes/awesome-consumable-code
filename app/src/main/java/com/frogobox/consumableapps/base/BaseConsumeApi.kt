package com.frogobox.consumableapps.base

import androidx.appcompat.app.AppCompatActivity
import com.frogobox.frogonewsapi.ConsumeNewsApi
import com.frogobox.frogonewsapi.util.NewsUrl
import com.frogobox.frogothemoviedbapi.ConsumeMovieApi
import com.frogobox.frogothemoviedbapi.util.MovieUrl

/*
 * Created by Faisal Amir
 * =========================================
 * ConsumableApps
 * Copyright (C) 28/04/2020.      
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * -----------------------------------------
 * FrogoBox Inc
 * com.frogobox.consumableapps.base
 * 
 */
abstract class BaseConsumeApi : AppCompatActivity() {

    fun consumeMovieApi(): ConsumeMovieApi {
        val consumeMovieApi = ConsumeMovieApi(MovieUrl.API_KEY)
        consumeMovieApi.usingChuckInterceptor(this)
        return consumeMovieApi
    }

    fun consumeNewsApi(): ConsumeNewsApi {
        val consumeNewsApi = ConsumeNewsApi(NewsUrl.NEWS_API_KEY)
        consumeNewsApi.usingChuckInterceptor(this)
        return consumeNewsApi
    }

}