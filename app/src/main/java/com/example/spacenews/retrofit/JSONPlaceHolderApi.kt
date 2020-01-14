package com.example.spacenews.retrofit

import com.example.spacenews.retrofit.pojos.SpaceNewsPost
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface JSONPlaceHolderApi {
    @GET("/api/space/news.get")
    fun getPostWithID(@Query("index") id: Int): Call<SpaceNewsPost?>?

    @GET("/api/space/news.get")
    fun getPosts(): Call<List<SpaceNewsPost?>>?

    @GET("/api/space/news.get")
    fun getFilteredPosts(@Query("q") q: String): Call<List<SpaceNewsPost?>>?
}