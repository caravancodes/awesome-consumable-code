package com.frogobox.consumableapps.consumable.themoviedbapi

import android.os.Bundle
import com.frogobox.consumableapps.R
import com.frogobox.consumableapps.base.BaseActivity
import com.frogobox.consumableapps.consumable.themoviedbapi.fragment.movie.TrendingMovieFragment
import com.frogobox.consumableapps.consumable.themoviedbapi.fragment.person.TrendingPersonFragment
import com.frogobox.consumableapps.consumable.themoviedbapi.fragment.tv.TrendingTvFragment
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setupBottomNav(R.id.framelayout_main_container)
        setupFragment(savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        supportActionBar?.elevation = 0f
    }

    private fun setupFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            bottom_nav_main_menu.selectedItemId = R.id.bottom_menu_tv
        }
    }


    private fun setupBottomNav(frameLayout: Int) {
        bottom_nav_main_menu.clearAnimation()
        bottom_nav_main_menu.itemIconTintList = null
        bottom_nav_main_menu.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.bottom_menu_movie -> {
                    setupDetailActivity(resources.getString(R.string.title_movie))
                    setupChildFragment(
                        frameLayout,
                        TrendingMovieFragment()
                    )
                }

                R.id.bottom_menu_person -> {
                    setupDetailActivity(resources.getString(R.string.title_person))
                    setupChildFragment(
                        frameLayout,
                        TrendingPersonFragment()
                    )
                }

                R.id.bottom_menu_tv -> {
                    setupDetailActivity(resources.getString(R.string.title_tv))
                    setupChildFragment(
                        frameLayout,
                        TrendingTvFragment()
                    )
                }
            }

            true
        }

    }

}