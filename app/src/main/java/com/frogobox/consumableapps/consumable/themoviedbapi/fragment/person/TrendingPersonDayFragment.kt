package com.frogobox.consumableapps.consumable.themoviedbapi.fragment.person

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.frogobox.consumableapps.R
import com.frogobox.consumableapps.base.BaseFragment
import com.frogobox.frogothemoviedbapi.callback.MovieResultCallback
import com.frogobox.frogothemoviedbapi.data.model.TrendingPerson
import com.frogobox.frogothemoviedbapi.data.response.Trending
import com.frogobox.frogothemoviedbapi.util.MovieUrl
import com.frogobox.recycler.boilerplate.viewrclass.FrogoViewAdapterCallback
import kotlinx.android.synthetic.main.list_movie.view.*
import kotlinx.android.synthetic.main.fragment_movie_trending_child.*

/**
 * A simple [Fragment] subclass.
 */
class TrendingPersonDayFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_trending_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTrendingPersonWeek()
    }

    private fun getTrendingPersonWeek() {
        consumeMovieApi().getTrendingPersonWeek(object :
            MovieResultCallback<Trending<TrendingPerson>> {
            override fun getResultData(data: Trending<TrendingPerson>) {
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

    private fun setupAdapter(data: List<TrendingPerson>) {
        val adapterCallback = object : FrogoViewAdapterCallback<TrendingPerson> {
            override fun onItemClicked(data: TrendingPerson) {
                data.name?.let { showToast(it) }
            }

            override fun onItemLongClicked(data: TrendingPerson) {
            }

            override fun setupInitComponent(view: View, data: TrendingPerson) {
                view.tv_title.text = data.name
                view.tv_overview.text = data.known_for_department
                Glide.with(view.context)
                    .load("${MovieUrl.BASE_URL_IMAGE_ORIGNAL}${data.profile_path}")
                    .into(view.iv_poster)
            }
        }

        frogoRecyclerView.injector<TrendingPerson>()
            .addData(data)
            .addCustomView(R.layout.list_movie)
            .addEmptyView(null)
            .addCallback(adapterCallback)
            .createLayoutGrid(2)
            .build()
    }

}
