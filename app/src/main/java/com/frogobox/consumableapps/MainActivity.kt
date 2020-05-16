package com.frogobox.consumableapps

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.frogobox.consumableapps.base.BaseActivity
import com.frogobox.consumableapps.consumable.newsapi.NewsActivity
import com.frogobox.consumableapps.consumable.themealdbapi.MealActivity
import com.frogobox.consumableapps.consumable.themoviedbapi.MovieActivity
import com.frogobox.consumableapps.consumable.thesportdbapi.SportActivity
import com.frogobox.consumableapps.model.Library
import com.frogobox.consumableapps.util.Constant
import com.frogobox.consumableapps.util.Helper
import com.frogobox.recycler.boilerplate.viewrclass.FrogoViewAdapterCallback

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_main.view.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()

    }

    private fun listData(): MutableList<Library> {
        val listData = mutableListOf<Library>()
        val rawJsonData = Helper.getJsonAssets(this, "library.json")
        val listLibraryType = object : TypeToken<List<Library>>() {}.type
        val tempList: List<Library> = Gson().fromJson(rawJsonData, listLibraryType)
        listData.addAll(tempList)
        return listData
    }

    private fun setupRecyclerView() {

        val adapterCallback = object : FrogoViewAdapterCallback<Library> {
            override fun onItemClicked(data: Library) {
                setupIntentActivity(data.code, data)
            }

            override fun onItemLongClicked(data: Library) {
            }

            override fun setupInitComponent(view: View, data: Library) {
                Glide.with(this@MainActivity).load(data.image).into(view.iv_icon)
                view.tv_title.text = data.name
                view.tv_link.text = data.link
            }
        }

        rv_main.injector<Library>()
            .addData(listData())
            .addCustomView(R.layout.list_main)
            .addEmptyView(null)
            .addCallback(adapterCallback)
            .createLayoutLinearVertical(false)
            .build()

    }

    private fun setupIntentActivity(codeActivity: Int, data: Library) {
        when (codeActivity) {

            0 -> {
                baseStartActivity<NewsActivity, Library>(Constant.EXTRA_MAIN, data)
            }
            1 -> {
                baseStartActivity<MovieActivity, Library>(Constant.EXTRA_MAIN, data)
            }
            2 -> {
                baseStartActivity<SportActivity, Library>(Constant.EXTRA_MAIN, data)
            }
            3 -> {
                baseStartActivity<MealActivity, Library>(Constant.EXTRA_MAIN, data)
            }
            4 -> {
            }
            5 -> {
            }
            6 -> {
            }
            7 -> {
            }
        }

    }

}