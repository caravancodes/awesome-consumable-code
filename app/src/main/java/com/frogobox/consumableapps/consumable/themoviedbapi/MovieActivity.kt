package com.frogobox.consumableapps.consumable.themoviedbapi

import android.os.Bundle
import com.frogobox.consumableapps.R
import com.frogobox.consumableapps.base.BaseActivity

class MovieActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setupDetailTitle()
    }
}