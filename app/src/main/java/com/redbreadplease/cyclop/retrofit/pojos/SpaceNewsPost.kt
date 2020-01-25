package com.redbreadplease.cyclop.retrofit.pojos

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class SpaceNewsPost {
    @SerializedName("annotation")
    @Expose
    var annotation: String? = null
    @SerializedName("photo_urls")
    @Expose
    var photo_urls: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("body")
    @Expose
    var body: String? = null
}