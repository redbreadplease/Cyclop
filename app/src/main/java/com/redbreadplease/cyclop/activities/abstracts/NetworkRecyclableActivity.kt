package com.redbreadplease.cyclop.activities.abstracts

import android.app.Service
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.R.id.*
import com.redbreadplease.cyclop.activities.VRActivity
import com.redbreadplease.cyclop.retrofit.NetworkService
import com.redbreadplease.cyclop.retrofit.pojos.GalleryPhoto
import com.redbreadplease.cyclop.retrofit.pojos.NewsPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread


abstract class NetworkRecyclableActivity : RecyclableActivity() {
    private lateinit var searchButton: Button
    private lateinit var hideSearchFieldButton: Button
    private lateinit var searchField: EditText
    private lateinit var searchFrame: ConstraintLayout
    private lateinit var toolbarName: TextView
    private lateinit var entranceARModeButton: Button
    private lateinit var defaultActivityBackground: ImageView

    @Suppress("UNUSED")
    private val toolbarTransitionsAnimationDuration = 1000L

    override fun tryToShowNews() {
        showNews()
        Thread.sleep(100)
        if (!isNewsAdapterSet())
            showNews()
    }

    override fun tryToShowResults(userRequest: String) {
        val request: String = "\"" + userRequest + "\""
        showFilteredPosts(request)
        Thread.sleep(100)
        if (!isSearchAdapterSet())
            showFilteredPosts(request)
    }

    override fun tryToShowGallery() {
        showPhotos()
        Thread.sleep(100)
        if (!isGalleryAdapterSet())
            showPhotos()
    }

    override fun setNewsActivityClickableZones() {
        if (!::hideSearchFieldButton.isInitialized)
            hideSearchFieldButton = findViewById<Button>(hide_search_field_button).apply {
                this.setOnClickListener {
                    (applicationContext.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                        searchField.applicationWindowToken, 0
                    )
                    searchField.isEnabled = true
                    searchFrame.visibility = View.GONE
                    searchRecyclerView.visibility = View.GONE
                    toolbarName.visibility = View.VISIBLE
                    newsRecyclerView.visibility = View.VISIBLE
                }
            }
        if (!::searchButton.isInitialized)
            searchButton = findViewById<Button>(search_button).apply {
                this.setOnClickListener {
                    if (newsRecyclerView.visibility == View.VISIBLE) {
                        searchField.requestFocus()
                        searchFrame.visibility = View.VISIBLE
                        toolbarName.visibility = View.GONE
                        newsRecyclerView.visibility = View.GONE
                        (applicationContext.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
                            searchField, 0
                        )
                    } else {
                        try {
                            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                                currentFocus!!.windowToken,
                                0
                            )
                        } catch (e: Exception) {
                            // TODO: handle exception
                        }
                        showProgressBar()
                        handleSearchRequest()
                    }
                }
            }
        if (!::defaultActivityBackground.isInitialized)
            defaultActivityBackground = findViewById<ImageView>(default_activity_background).apply {
                this.setOnClickListener {
                    (applicationContext.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                        searchField.applicationWindowToken, 0
                    )
                    searchField.isEnabled = true
                    searchFrame.visibility = View.GONE
                    searchRecyclerView.visibility = View.GONE
                    toolbarName.visibility = View.VISIBLE
                    newsRecyclerView.visibility = View.VISIBLE
                }
            }
        if (!::searchField.isInitialized)
            searchField = findViewById(search_field)
        if (!::searchFrame.isInitialized)
            searchFrame = findViewById(search_frame)
        if (!::toolbarName.isInitialized)
            toolbarName = findViewById(toolbar_name)
    }

    override fun setARMenuActivityClickableZones() {
        if (!::entranceARModeButton.isInitialized)
            entranceARModeButton = findViewById<Button>(ar_entrance_button).apply {
                this.setOnClickListener {
                    startActivity(Intent(applicationContext, VRActivity::class.java))
                }
            }
    }

    private fun showNews() {
        NetworkService.getInstance()
            .jsonApi
            ?.getPosts()
            ?.enqueue(object : Callback<List<NewsPost?>> {
                override fun onResponse(
                    call: Call<List<NewsPost?>>,
                    response: Response<List<NewsPost?>>
                ) {
                    hideProgressBar()
                    createToast("Loaded successfully")
                    setNewsAdapter(response.body() as MutableList<NewsPost?>)
                }

                override fun onFailure(call: Call<List<NewsPost?>>, t: Throwable) {
                    hideProgressBar()
                    createToast(resources.getString(R.string.errorWhileLoading))
                    t.printStackTrace()
                }
            })
    }

    private fun showFilteredPosts(userRequest: String) {
        NetworkService.getInstance()
            .jsonApi
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
                    hideProgressBar()
                    setSearchAdapter(content)
                }

                override fun onFailure(call: Call<List<NewsPost?>>, t: Throwable) {
                    hideProgressBar()
                    createToast("Error while loading")
                    t.printStackTrace()
                }
            })
    }


    private fun showPhotos() {
        NetworkService.getInstance()
            .jsonApi
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
                    hideProgressBar()
                    setGalleryAdapter(content)
                }

                override fun onFailure(call: Call<List<GalleryPhoto?>>, t: Throwable) {
                    hideProgressBar()
                    createToast("Error while loading")
                    t.printStackTrace()
                }
            })
    }

    override fun handleSearchRequest() {
        findViewById<EditText>(search_field).text.toString().also {
            if (it != "")
                thread {
                    tryToShowResults(it)
                }
        }
    }
}