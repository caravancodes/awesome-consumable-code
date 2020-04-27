package com.frogobox.consumableapps.consumable.themoviedbapi.fragment.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.frogobox.consumableapps.R
import com.frogobox.consumableapps.base.BaseFragment
import com.frogobox.frogothemoviedbapi.callback.MovieResultCallback
import com.frogobox.frogothemoviedbapi.data.model.TrendingTv
import com.frogobox.frogothemoviedbapi.data.response.Trending
import com.frogobox.frogothemoviedbapi.util.MovieUrl
import com.frogobox.recycler.boilerplate.adapter.callback.FrogoAdapterCallback
import kotlinx.android.synthetic.main.list_movie.view.*
import kotlinx.android.synthetic.main.fragment_movie_trending_child.*


class TrendingTvDayFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_trending_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTrendingTvDay()
    }

    private fun getTrendingTvDay() {
        consumeMovieApi().getTrendingTvDay(object :
            MovieResultCallback<Trending<TrendingTv>> {
            override fun getResultData(data: Trending<TrendingTv>) {
                data.results?.let { setupAdapter(it) }
            }

            override fun failedResult(statusCode: Int, errorMessage: String?) {
                errorMessage?.let { showToast(it) }
            }

            override fun onShowProgress() {
                showProgressThread(progress_view)
            }

            override fun onHideProgress() {
                hideProgressThread(progress_view)
            }
        })
    }

    private fun setupAdapter(data: List<TrendingTv>) {

        val adapterCallback = object : FrogoAdapterCallback<TrendingTv> {
            override fun onItemClicked(data: TrendingTv) {
                data.original_name?.let { showToast(it) }
            }

            override fun onItemLongClicked(data: TrendingTv) {
            }

            override fun setupInitComponent(view: View, data: TrendingTv) {
                view.tv_title.text = data.name
                view.tv_overview.text = data.overview
                Glide.with(view.context)
                    .load("${MovieUrl.BASE_URL_IMAGE_ORIGNAL}${data.poster_path}")
                    .into(view.iv_poster)
            }
        }

        frogoRecyclerView.injector<TrendingTv>()
            .addData(data)
            .addCustomView(R.layout.list_movie)
            .addEmptyView(null)
            .addCallback(adapterCallback)
            .createLayoutGrid(2)
            .createAdapter()
            .build(frogoRecyclerView)

    }

}
