package com.frogobox.consumableapps.consumable.newsapi

import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.frogobox.consumableapps.R
import com.frogobox.consumableapps.base.BaseActivity
import com.frogobox.frogonewsapi.callback.NewsResultCallback
import com.frogobox.frogonewsapi.data.model.Article
import com.frogobox.frogonewsapi.data.response.ArticleResponse
import com.frogobox.frogonewsapi.util.NewsConstant
import com.frogobox.recycler.core.IFrogoViewAdapter
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.list_main.view.tv_title
import kotlinx.android.synthetic.main.list_news_article_vertical.view.*
import kotlinx.android.synthetic.main.list_news_category.view.*

class NewsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        setupDetailTitle()
        setupFrogoRvCategory()
        getTopHeadline(NewsConstant.CATEGORY_ENTERTAIMENT) { setupFrogoRvHorizontal(it) }
        getTopHeadline(null) { setupFrogoRvVertical(it) }

        supportActionBar?.elevation = 0f
    }

    private fun listCategory(): MutableList<String> {
        val categories = mutableListOf<String>()
        categories.add(NewsConstant.CATEGORY_BUSINESS)
        categories.add(NewsConstant.CATEGORY_HEALTH)
        categories.add(NewsConstant.CATEGORY_ENTERTAIMENT)
        categories.add(NewsConstant.CATEGORY_GENERAL)
        categories.add(NewsConstant.CATEGORY_SCIENCE)
        categories.add(NewsConstant.CATEGORY_SPORTS)
        categories.add(NewsConstant.CATEGORY_TECHNOLOGY)
        return categories
    }

    private fun setupFrogoRvCategory() {

        val categoryAdapter = object : IFrogoViewAdapter<String> {
            override fun onItemClicked(data: String) {
                getTopHeadline(data) { setupFrogoRvVertical(it) }
                tv_top_headline.text = "category $data"
            }

            override fun onItemLongClicked(data: String) {
                showToast(data)
            }

            override fun setupInitComponent(view: View, data: String) {
                view.tv_category.text = data
            }
        }

        rv_category.injector<String>()
            .addData(listCategory())
            .addCustomView(R.layout.list_news_category)
            .addEmptyView(null)
            .addCallback(categoryAdapter)
            .createLayoutLinearHorizontal(false)
            .build()

    }

    private fun setupAdapterCallback(): IFrogoViewAdapter<Article> {

        val newsGeneralAdapterCallback = object : IFrogoViewAdapter<Article> {
            override fun onItemClicked(data: Article) {}

            override fun onItemLongClicked(data: Article) {}

            override fun setupInitComponent(view: View, data: Article) {
                view.tv_title.text = data.title
                view.tv_description.text = data.publishedAt
                view.tv_published.text = data.description
                Glide.with(view.context).load(data.urlToImage).into(view.iv_url)
            }
        }
        return newsGeneralAdapterCallback
    }

    private fun setupFrogoRvHorizontal(data: List<Article>) {
        rv_news_general.injector<Article>()
            .addData(data)
            .addCustomView(R.layout.list_news_article_horizontal)
            .addEmptyView(null)
            .addCallback(setupAdapterCallback())
            .createLayoutLinearHorizontal(false)
            .build()
    }

    private fun setupFrogoRvVertical(data: List<Article>) {
        rv_news_category.injector<Article>()
            .addData(data)
            .addCustomView(R.layout.list_news_article_vertical)
            .addEmptyView(null)
            .addCallback(setupAdapterCallback())
            .createLayoutLinearVertical(false)
            .build()
    }

    private fun getTopHeadline(category: String?, frogoRvSetup: (data: List<Article>) -> Unit) {
        consumeNewsApi().getTopHeadline( // Adding Base Parameter on main function
            null,
            null,
            category,
            NewsConstant.COUNTRY_ID,
            null,
            null,
            object : NewsResultCallback<ArticleResponse> {
                override fun getResultData(data: ArticleResponse) {
                    // Your Ui or data
                    data.articles?.let { frogoRvSetup(it) }
                }

                override fun failedResult(statusCode: Int, errorMessage: String?) {
                    // Your failed to do
                    errorMessage?.let { showToast(it) }
                }

                override fun onShowProgress() {
                    // Your Progress Show
                    Log.d("RxJavaShow", "Show Progress")
                    runOnUiThread {
                        // Stuff that updates the UI
                        progress_view.visibility = View.VISIBLE
                    }
                }

                override fun onHideProgress() {
                    // Your Progress Hide
                    Log.d("RxJavaHide", "Hide Progress")
                    runOnUiThread {
                        // Stuff that updates the UI
                        progress_view.visibility = View.GONE
                    }

                }

            })

    }

}