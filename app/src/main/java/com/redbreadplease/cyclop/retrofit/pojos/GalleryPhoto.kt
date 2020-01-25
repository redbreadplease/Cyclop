package com.redbreadplease.cyclop.retrofit.pojos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GalleryPhoto {
    @SerializedName("annotation")
    @Expose
    var annotation: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null
}