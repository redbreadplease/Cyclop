package com.redbreadplease.cyclop.abstracts

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.activities.ARSpaceActivity
import com.redbreadplease.cyclop.retrofit.NetworkService
import com.redbreadplease.cyclop.retrofit.pojos.GalleryPhoto
import com.redbreadplease.cyclop.retrofit.pojos.NewsPost
import pl.droidsonroids.gif.GifImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

abstract class NetworkRecyclableActivity : RecyclableActivity() {
    private var searchButton: Button? = null
    private var entranceARModeButton: Button? = null

    override fun tryToShowNews() {
        findViewById<GifImageView>(R.id.loading_gif).setVisibility(View.VISIBLE)
        showNews()
        Thread.sleep(100)
        if (!isNewsAdapterSet())
            showNews()
    }

    override fun tryToShowResults(userRequest: String) {
        findViewById<GifImageView>(R.id.loading_gif).setVisibility(View.VISIBLE)
        val request: String = "\"" + userRequest + "\""
        showFilteredPosts(request)
        Thread.sleep(100)
        if (!isNewsAdapterSet())
            showFilteredPosts(request)
    }

    override fun tryToShowGallery() {
        findViewById<GifImageView>(R.id.loading_gif).setVisibility(View.VISIBLE)
        showPhotos()
        Thread.sleep(100)
        if (!isGalleryAdapterSet())
            showPhotos()
    }

    override fun setSearchActivityClickableZones() {
        if (searchButton == null) {
            searchButton = findViewById(R.id.search_posts_button)
        }
        searchButton?.setOnClickListener {
            try {
                val imm =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            } catch (e: Exception) { // TODO: handle exception
            }

            val searchFieldText: String =
                findViewById<EditText>(R.id.search_text_request_frame).getText().toString()
            tryToShowResults(searchFieldText)
        }
    }

    override fun setARMenuActivityClickableZones() {
        if (entranceARModeButton == null) {
            entranceARModeButton = findViewById(R.id.ar_entrance_button)
        }
        entranceARModeButton?.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Apply activity transition
                createToast("You\'re able to apply transition")
            } else {
                // Swap without transition
                createToast("You have to swap without transition")
            }
            startActivity(Intent(this, ARSpaceActivity::class.java))
        }
    }

    private fun showNews() {
        NetworkService.getInstance()
            .getJSONApi()
            ?.getPosts()
            ?.enqueue(object : Callback<List<NewsPost?>> {
                override fun onResponse(
                    call: Call<List<NewsPost?>>,
                    response: Response<List<NewsPost?>>
                ) {
                    createToast("Loaded successfully")
                    val posts: List<NewsPost?>? = response.body()
                    val content = mutableListOf<NewsPost?>()
                    if (posts != null)
                        for (post: NewsPost? in posts)
                            content.add(post)
                    setNewsAdapter(content)
                }

                override fun onFailure(call: Call<List<NewsPost?>>, t: Throwable) {
                    createToast("Error while loading")
                    t.printStackTrace()
                }

            })
    }

    private fun showFilteredPosts(userRequest: String) {
        NetworkService.getInstance()
            .getJSONApi()
            ?.getFilteredPosts(userRequest)
            ?.enqueue(object : Callback<List<NewsPost?>> {
                override fun onResponse(
                    call: Call<List<NewsPost?>>,
                    response: Response<List<NewsPost?>>
                ) {
                    createToast("Loaded successfully")
                    val posts: List<NewsPost?>? = response.body()
                    val content = mutableListOf<NewsPost?>()
                    if (posts != null)
                        for (post: NewsPost? in posts)
                            content.add(post)
                    setNewsAdapter(content)
                }

                override fun onFailure(call: Call<List<NewsPost?>>, t: Throwable) {
                    createToast("Error while loading")
                    t.printStackTrace()
                }
            })
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
                    setGalleryAdapter(content)
                }

                override fun onFailure(call: Call<List<GalleryPhoto?>>, t: Throwable) {
                    createToast("Error while loading")
                    t.printStackTrace()
                }
            })
    }

    override fun handleSearchRequest() {
        val requestBody: String =
            findViewById<EditText>(R.id.search_text_request_frame).getText().toString()
        if (requestBody != "")
            thread {
                tryToShowResults(requestBody)
            }
    }
}