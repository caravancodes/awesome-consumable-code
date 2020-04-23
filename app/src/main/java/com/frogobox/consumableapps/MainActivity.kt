package com.frogobox.consumableapps

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.frogobox.consumableapps.base.BaseActivity
import com.frogobox.consumableapps.consumable.newsapi.NewsActivity
import com.frogobox.consumableapps.consumable.thesportdbapi.SportActivity
import com.frogobox.frogonewsapi.ConsumeNewsApi
import com.frogobox.frogonewsapi.callback.NewsResultCallback
import com.frogobox.frogonewsapi.data.response.ArticleResponse
import com.frogobox.frogonewsapi.util.NewsUrl
import com.frogobox.recycler.callback.FrogoAdapterCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_list_main.view.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        setupNewsApi()
    }

    private fun setupNewsApi() {
        val consumeNewsApi = ConsumeNewsApi(NewsUrl.NEWS_API_KEY)
        consumeNewsApi.usingChuckInterceptor(this)
        consumeNewsApi.getTopHeadline(
            null,
            null,
            null,
            null,
            null,
            null,
            object : NewsResultCallback<ArticleResponse> {
                override fun failedResult(statusCode: Int, errorMessage: String?) {

                }

                override fun getResultData(data: ArticleResponse) {

                }

                override fun onHideProgress() {

                }

                override fun onShowProgress() {

                }
            })
    }


    private fun listData(): MutableList<Library> {
        val listData = mutableListOf<Library>()

        listData.add(
            Library(
                R.drawable.ic_launcher_background,
                "News Api",
                "https://github.com/amirisback/consumable-code-news-api",
                0
            )
        )
        listData.add(
            Library(
                R.drawable.ic_launcher_background,
                "Sport",
                "https://github.com/amirisback/consumable-code-news-api",
                1
            )
        )
        listData.add(
            Library(
                R.drawable.ic_launcher_background,
                "News Api",
                "https://github.com/amirisback/consumable-code-news-api",
                2
            )
        )

        return listData
    }

    private fun setupRecyclerView() {
        rv_main.injectAdapter(
            R.layout.content_list_main,
            listData(),
            null,
            object : FrogoAdapterCallback<Library> {
                override fun onItemClicked(data: Library) {

                    when (data.code) {
                        0 -> {
                            startActivity(Intent(this@MainActivity, NewsActivity::class.java))
                        }
                        1 -> {
                            startActivity(Intent(this@MainActivity, SportActivity::class.java))
                        }
                        2 -> {
                            startActivity(Intent(this@MainActivity, NewsActivity::class.java))
                        }
                        3 -> {
                            startActivity(Intent(this@MainActivity, NewsActivity::class.java))
                        }
                        4 -> {
                            startActivity(Intent(this@MainActivity, NewsActivity::class.java))
                        }
                        5 -> {
                            startActivity(Intent(this@MainActivity, NewsActivity::class.java))
                        }
                        6 -> {
                            startActivity(Intent(this@MainActivity, NewsActivity::class.java))
                        }
                        7 -> {
                            startActivity(Intent(this@MainActivity, NewsActivity::class.java))
                        }
                    }


                }

                override fun onItemLongClicked(data: Library) {

                }

                override fun setupInitComponent(view: View, data: Library) {
                    view.iv_icon.setImageResource(data.image)
                    view.tv_title.text = data.name
                    view.tv_link.text = data.link
                }
            }
        )
        rv_main.isViewLinearVertical(false)
    }

}
