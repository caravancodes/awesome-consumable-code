package com.frogobox.consumableapps.consumable.themoviedbapi.fragment.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.frogobox.consumableapps.R
import com.frogobox.consumableapps.base.BaseFragment
import com.frogobox.frogothemoviedbapi.callback.MovieResultCallback
import com.frogobox.frogothemoviedbapi.data.model.TrendingMovie
import com.frogobox.frogothemoviedbapi.data.response.Trending
import com.frogobox.frogothemoviedbapi.util.MovieUrl
import com.frogobox.recycler.boilerplate.adapter.callback.FrogoAdapterCallback
import kotlinx.android.synthetic.main.list_movie.view.*
import kotlinx.android.synthetic.main.fragment_movie_trending_child.*

/**
 * A simple [Fragment] subclass.
 */
class TrendingMovieWeekFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_trending_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTrendingMovieWeek()
    }

    private fun getTrendingMovieWeek() {
        consumeMovieApi().getTrendingMovieWeek(object :
            MovieResultCallback<Trending<TrendingMovie>> {
            override fun getResultData(data: Trending<TrendingMovie>) {
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

    private fun setupAdapter(data: List<TrendingMovie>) {
        val adapterCallback = object : FrogoAdapterCallback<TrendingMovie> {
            override fun onItemClicked(data: TrendingMovie) {
                data.original_title?.let { showToast(it) }
            }

            override fun onItemLongClicked(data: TrendingMovie) {
            }

            override fun setupInitComponent(view: View, data: TrendingMovie) {
                view.tv_title.text = data.title
                view.tv_overview.text = data.overview
                Glide.with(view.context)
                    .load("${MovieUrl.BASE_URL_IMAGE_ORIGNAL}${data.poster_path}")
                    .into(view.iv_poster)
            }
        }

        frogoRecyclerView.injector<TrendingMovie>()
            .addData(data)
            .addCustomView(R.layout.list_movie)
            .addEmptyView(null)
            .addCallback(adapterCallback)
            .createLayoutGrid(2)
            .createAdapter()
            .build(frogoRecyclerView)
    }

}
