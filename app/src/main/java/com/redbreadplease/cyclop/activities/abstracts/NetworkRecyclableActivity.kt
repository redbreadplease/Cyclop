package com.redbreadplease.cyclop.activities.abstracts

import android.R.id.edit
import android.animation.LayoutTransition
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.cardview.widget.CardView
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

    private val toolbarTransitionsAnimationDuration = 1000.toLong()

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
        runOnUiThread {
            //TODO
        }
        showPhotos()
        Thread.sleep(100)
        if (!isGalleryAdapterSet())
            showPhotos()
    }

    override fun setNewsActivityClickableZones() {
        if (!::hideSearchFieldButton.isInitialized) {
            hideSearchFieldButton = findViewById(hide_search_field_button)
            hideSearchFieldButton.setOnClickListener {
                (applicationContext.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    searchField.applicationWindowToken, 0
                )
                searchField.setEnabled(true)
                searchFrame.visibility = View.GONE
                searchRecyclerview.visibility = View.GONE
                toolbarName.visibility = View.VISIBLE
                newsRecyclerview.visibility = View.VISIBLE
            }
        }
        if (!::searchButton.isInitialized) {
            searchButton = findViewById(search_button)
            searchButton.setOnClickListener {
                if (newsRecyclerview.visibility == View.VISIBLE) {
                    searchField.requestFocus()
                    searchFrame.visibility = View.VISIBLE
                    toolbarName.visibility = View.GONE
                    newsRecyclerview.visibility = View.GONE
                    (applicationContext.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
                        searchField, 0
                    )
                } else {
                    try {
                        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                            currentFocus!!.windowToken,
                            0
                        )
                    } catch (e: Exception) { // TODO: handle exception
                    }
                    showProgressBar()
                    handleSearchRequest()
                }
            }
        }
        if (!::defaultActivityBackground.isInitialized) {
            defaultActivityBackground = findViewById(default_activity_background)
            defaultActivityBackground.also {
                it.setOnClickListener {
                    (applicationContext.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                        searchField.applicationWindowToken, 0
                    )
                    searchField
                        .setEnabled(true)
                    searchFrame.visibility = View.GONE
                    searchRecyclerview.visibility = View.GONE
                    toolbarName.visibility = View.VISIBLE
                    newsRecyclerview.visibility = View.VISIBLE
                }
            }
        }
        if (!::searchField.isInitialized)
            searchField = findViewById(search_field)
        if (!::searchFrame.isInitialized)
            searchFrame = findViewById(search_frame)
        if (!::toolbarName.isInitialized)
            toolbarName = findViewById(toolbar_name)
    }

    override fun setSearchActivityClickableZones() {

    }

    override fun setARMenuActivityClickableZones() {
        if (!::entranceARModeButton.isInitialized) {
            entranceARModeButton = findViewById(ar_entrance_button)
        }
        entranceARModeButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // TODO
                // Apply activity transition
            } else {
                // TODO
                // Swap without transition
            }
            startActivity(Intent(this, VRActivity::class.java))
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
            findViewById<EditText>(search_field).text.toString()
        if (requestBody != "") {
            thread {
                tryToShowResults(requestBody)
            }
        }
    }
}