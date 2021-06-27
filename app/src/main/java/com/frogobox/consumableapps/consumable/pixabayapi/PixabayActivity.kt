package com.frogobox.consumableapps.consumable.pixabayapi

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.frogobox.consumableapps.R
import com.frogobox.consumableapps.base.BaseActivity
import com.frogobox.frogopixabayapi.callback.PixabayResultCallback
import com.frogobox.frogopixabayapi.data.model.PixabayImage
import com.frogobox.frogopixabayapi.data.response.Response
import com.frogobox.recycler.core.IFrogoViewAdapter
import kotlinx.android.synthetic.main.activity_pixabay.*
import kotlinx.android.synthetic.main.list_pixabay.view.*

class PixabayActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pixabay)
        setupDetailTitle()
        consumePixabay("Nature")
    }

    private fun consumePixabay(query: String) {
        consumePixabayApi().searchImage(
            query,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            object : PixabayResultCallback<Response<PixabayImage>> {
                override fun failedResult(statusCode: Int, errorMessage: String?) {
                    errorMessage?.let { showToast(it) }
                }

                override fun getResultData(data: Response<PixabayImage>) {
                    data.hits?.let { setupFrogoRecyclerView(it) }
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
            }
        )
    }

    private fun setupFrogoRecyclerView(data: List<PixabayImage>) {
        frogo_pixabay.injector<PixabayImage>()
            .addData(data)
            .addCustomView(R.layout.list_pixabay)
            .addEmptyView(null)
            .addCallback(object : IFrogoViewAdapter<PixabayImage> {
                override fun onItemClicked(data: PixabayImage) {}

                override fun onItemLongClicked(data: PixabayImage) {}

                override fun setupInitComponent(view: View, data: PixabayImage) {
                    val sumLikes = data.likes.toString()
                    view.tv_pixabay_text_1.text = data.tags
                    view.tv_pixabay_text_2.text = "$sumLikes likes"
                    Glide.with(view.context).load(data.largeImageURL).into(view.iv_pixabay_poster)
                }
            })
            .createLayoutStaggeredGrid(2)
            .build()
    }

}