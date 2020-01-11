package com.example.spacenews.abstracts

import com.example.spacenews.enums.ButtonsPurposes
import com.example.spacenews.retrofit.NetworkService
import com.example.spacenews.retrofit.pojos.SpaceNewsPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

abstract class RecyclableNetworkActivity : RecyclableActivity() {
    override fun prepareActivityView() {
        setSelectedItemMenuIcon(ButtonsPurposes.NEWS)
    }

    fun tryToShowNews() {
        thread {
            setRecyclerView()
            showNewsTitles()
            if (adapter == null)
                showNewsTitles()
        }
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
                    println("!!!!It\'s all ok!")
                    val posts: List<SpaceNewsPost?>? = response.body()
                    val content = mutableListOf<SpaceNewsPost?>()
                    if (posts != null)
                        for (i in 0..2)
                            for (post: SpaceNewsPost? in posts)
                                content.add(post)
                    setAdapter(content)
                }

                override fun onFailure(call: Call<List<SpaceNewsPost?>>, t: Throwable) {
                    println("!!!!Something has gone wrong")
                    t.printStackTrace()
                }
            })
    }

}