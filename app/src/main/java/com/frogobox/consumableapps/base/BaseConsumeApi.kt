package com.frogobox.consumableapps.base

import androidx.appcompat.app.AppCompatActivity
import com.frogobox.frogonewsapi.ConsumeNewsApi
import com.frogobox.frogonewsapi.util.NewsUrl
import com.frogobox.frogopixabayapi.ConsumePixabayApi
import com.frogobox.frogopixabayapi.util.PixabayConstant
import com.frogobox.frogopixabayapi.util.PixabayUrl
import com.frogobox.frogothemealdbapi.ConsumeTheMealDbApi
import com.frogobox.frogothemealdbapi.util.MealConstant
import com.frogobox.frogothemealdbapi.util.MealUrl
import com.frogobox.frogothemoviedbapi.ConsumeMovieApi
import com.frogobox.frogothemoviedbapi.util.MovieUrl
import com.frogobox.frogothesportdbapi.ConsumeTheSportDbApi
import com.frogobox.frogothesportdbapi.util.SportUrl

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

    fun consumeSportApi(): ConsumeTheSportDbApi {
        val consumeTheSportDbApi = ConsumeTheSportDbApi(SportUrl.API_KEY)
        consumeTheSportDbApi.usingChuckInterceptor(this)
        return consumeTheSportDbApi
    }

    fun consumeMealApi(): ConsumeTheMealDbApi {
        val consumeTheMealDbApi = ConsumeTheMealDbApi("1")
        consumeTheMealDbApi.usingChuckInterceptor(this)
        return consumeTheMealDbApi
    }

    fun consumePixabayApi() : ConsumePixabayApi {
        val consumePixabayApi = ConsumePixabayApi(PixabayConstant.SAMPLE_API_KEY)
        consumePixabayApi.usingChuckInterceptor(this)
        return consumePixabayApi
    }

}