package com.redbreadplease.cyclop.activities.abstracts

import com.redbreadplease.cyclop.retrofit.NetworkService
import com.redbreadplease.cyclop.retrofit.pojos.GalleryPhoto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class RecyclableNetworkGalleryActivity : RecyclableGalleryActivity() {
    fun tryToShowGallery() {
        showPhotos()
        Thread.sleep(100)
        if (!isAdapterSet())
            showPhotos()
    }

    private fun showPhotos() {
        NetworkService.getInstance()
            .getJSONApi()
            ?.getCyclopGalleryPhotos()
            ?.enqueue(object : Callback<List<GalleryPhoto?>> {
                override fun onResponse(
                    call: Call<List<GalleryPhoto?>>,
                    response: Response<List<GalleryPhoto?>>
                ) {
                    createToast("Loaded successfully")
                    val photos: List<GalleryPhoto?>? = response.body()
                    val content = mutableListOf<GalleryPhoto?>()
                    if (photos != null)
                        for (photo: GalleryPhoto? in photos)
                            content.add(photo)
                    setAdapter(content)
                }

                override fun onFailure(call: Call<List<GalleryPhoto?>>, t: Throwable) {
                    createToast("Error while loading")
                    t.printStackTrace()
                }
            })
    }
}