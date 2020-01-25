package com.redbreadplease.cyclop.activities.abstracts

import com.redbreadplease.cyclop.retrofit.NetworkService
import com.redbreadplease.cyclop.retrofit.pojos.SpaceNewsPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


abstract class RecyclableNewsActivity : RecyclableActivity() {
    fun tryToShowNews() {
        showNewsTitles()
        Thread.sleep(100)
        if (!isAdapterSet())
            showNewsTitles()
    }

    private fun showNewsTitles() {
        NetworkService.getInstance()
            .getJSONApi()
            ?.getPosts()
            ?.enqueue(object : Callback<List<SpaceNewsPost?>> {
                override fun onResponse(
                    call: Call<List<SpaceNewsPost?>>,
                    response: Response<List<SpaceNewsPost?>>
                ) {
                    createToast("Loaded successfully")
                    val posts: List<SpaceNewsPost?>? = response.body()
                    val content = mutableListOf<SpaceNewsPost?>()
                    if (posts != null)
                        for (post: SpaceNewsPost? in posts)
                            content.add(post)
                    setAdapter(content)
                }

                override fun onFailure(call: Call<List<SpaceNewsPost?>>, t: Throwable) {
                    createToast("Error while loading")
                    t.printStackTrace()
                }
            })
    }
}