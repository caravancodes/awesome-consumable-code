package com.frogobox.consumableapps.consumable.themealdbapi

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.frogobox.consumableapps.R
import com.frogobox.consumableapps.base.BaseActivity
import com.frogobox.frogothemealdbapi.callback.MealResultCallback
import com.frogobox.frogothemealdbapi.data.model.Meal
import com.frogobox.frogothemealdbapi.data.response.MealResponse
import com.frogobox.recycler.boilerplate.viewrclass.FrogoViewAdapterCallback
import kotlinx.android.synthetic.main.activity_meal.*
import kotlinx.android.synthetic.main.list_meal.view.*

class MealActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)
        setupDetailTitle()
        setupConsumableMeal("b")
    }

    private fun setupConsumableMeal(firstLetter: String) {
        consumeMealApi().listAllMeal(
            firstLetter,
            object : MealResultCallback<MealResponse<Meal>> {
                override fun failedResult(statusCode: Int, errorMessage: String?) {
                    errorMessage?.let { showToast(it) }
                }

                override fun getResultData(data: MealResponse<Meal>) {
                    data.meals?.let { setupFrogoRecyclerView(it) }
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

    private fun setupFrogoRecyclerView(data: List<Meal>) {
        frogo_rv_meal.injector<Meal>()
            .addData(data)
            .addCustomView(R.layout.list_meal)
            .addEmptyView(null)
            .addCallback(object : FrogoViewAdapterCallback<Meal> {
                override fun onItemClicked(data: Meal) {

                }

                override fun onItemLongClicked(data: Meal) {

                }

                override fun setupInitComponent(view: View, data: Meal) {
                    view.tvMeal.text = data.strMeal
                    view.tvSubMeal.text = data.strCategory
                    Glide.with(view.context).load(data.strMealThumb).into(view.ivMeal)
                }
            })
            .createLayoutStaggeredGrid(2)
            .build()
    }

}