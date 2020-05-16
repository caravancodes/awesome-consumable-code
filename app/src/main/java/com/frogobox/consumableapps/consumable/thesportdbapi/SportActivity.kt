package com.frogobox.consumableapps.consumable.thesportdbapi

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.frogobox.consumableapps.R
import com.frogobox.consumableapps.base.BaseActivity
import com.frogobox.frogothesportdbapi.callback.SportResultCallback
import com.frogobox.frogothesportdbapi.data.model.Country
import com.frogobox.frogothesportdbapi.data.model.Sport
import com.frogobox.frogothesportdbapi.data.response.Countrys
import com.frogobox.frogothesportdbapi.data.response.Sports
import com.frogobox.recycler.boilerplate.viewrclass.FrogoViewAdapterCallback
import kotlinx.android.synthetic.main.activity_sport.*
import kotlinx.android.synthetic.main.list_news_category.view.*

class SportActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sport)
        setupDetailTitle()
        consumeSearchSport()
        consumeSearchLeagues("Soccer")
    }

    private fun consumeSearchSport() {
        consumeSportApi().getAllSports(object : SportResultCallback<Sports> {
            override fun failedResult(statusCode: Int, errorMessage: String?) {
                errorMessage?.let { showToast(it) }
            }

            override fun getResultData(data: Sports) {
                data.sports?.let { setupRvSport(it) }
            }

            override fun onHideProgress() {
                runOnUiThread {
                    progress_view.visibility = View.GONE
                }
            }

            override fun onShowProgress() {
                runOnUiThread {
                    progress_view.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun consumeSearchLeagues(sportCategory: String) {
        consumeSportApi().searchAllLeagues(
            null,
            sportCategory,
            object : SportResultCallback<Countrys> {
                override fun failedResult(statusCode: Int, errorMessage: String?) {
                    errorMessage?.let { showToast(it) }
                }

                override fun getResultData(data: Countrys) {
                    data.countrys?.let { setupRvLeagues(it) }
                }

                override fun onHideProgress() {
                    runOnUiThread {
                        progress_view.visibility = View.GONE
                    }
                }

                override fun onShowProgress() {
                    runOnUiThread {
                        progress_view.visibility = View.VISIBLE
                    }
                }
            })
    }

    private fun setupRvSport(data: List<Sport>) {
        rv_sport.injector<Sport>()
            .addData(data)
            .addCustomView(R.layout.list_sports_category)
            .addEmptyView(null)
            .addCallback(object : FrogoViewAdapterCallback<Sport> {
                override fun onItemClicked(data: Sport) {
                    data.strSport?.let { consumeSearchLeagues(it) }
                }

                override fun onItemLongClicked(data: Sport) {
                }

                override fun setupInitComponent(view: View, data: Sport) {
                    view.tv_category.text = data.strSport
                }
            })
            .createLayoutLinearHorizontal(false)
            .build()
    }

    private fun setupRvLeagues(data: List<Country>) {
        rv_leagues.injector<Country>()
            .addData(data)
            .addCustomView(R.layout.frogo_rv_list_type_6)
            .addEmptyView(null)
            .addCallback(object : FrogoViewAdapterCallback<Country> {
                override fun onItemClicked(data: Country) {}

                override fun onItemLongClicked(data: Country) {}

                override fun setupInitComponent(view: View, data: Country) {
                    val tvTitle = view.findViewById<TextView>(R.id.frogo_rv_type_6_tv_title)
                    val tvSubTitle = view.findViewById<TextView>(R.id.frogo_rv_type_6_tv_subtitle)
                    val tvDesc = view.findViewById<TextView>(R.id.frogo_rv_type_6_tv_description)
                    val ivPoster = view.findViewById<ImageView>(R.id.frogo_rv_type_6_iv_poster)

                    Glide.with(view.context).load(data.strBadge).into(ivPoster)
                    tvTitle.text = data.strLeague
                    tvSubTitle.text = data.strCountry
                    tvDesc.text = data.strDescriptionEN

                }
            })
            .createLayoutLinearVertical(false)
            .build()
    }

}