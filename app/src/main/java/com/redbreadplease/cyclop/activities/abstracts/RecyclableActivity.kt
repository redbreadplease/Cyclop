package com.redbreadplease.cyclop.activities.abstracts

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.stuff.ActivityType
import com.redbreadplease.cyclop.recyclerview.GalleryRecyclerviewAdapter
import com.redbreadplease.cyclop.recyclerview.RecyclerViewAdapter
import com.redbreadplease.cyclop.retrofit.pojos.GalleryPhoto
import com.redbreadplease.cyclop.retrofit.pojos.NewsPost
import kotlin.concurrent.thread

abstract class RecyclableActivity : BaseActivity() {
    protected lateinit var newsRecyclerview: RecyclerView
    private var newsAdapter: RecyclerViewAdapter? = null
    private lateinit var newsSwipeRefresher: SwipeRefreshLayout

    protected lateinit var searchRecyclerview: RecyclerView
    private lateinit var searchAdapter: RecyclerViewAdapter
    lateinit var searchSwipeRefresher: SwipeRefreshLayout

    private var galleryRecyclerview: RecyclerView? = null
    private var galleryAdapter: GalleryRecyclerviewAdapter? = null
    var gallerySwipeRefresher: SwipeRefreshLayout? = null

    private lateinit var centerProgressBar: ProgressBar

    fun setActivityView(activityType: ActivityType) {
        setNavbar()
        setActiveNavbarButton(activityType)
        when (activityType) {
            ActivityType.NEWS -> {
                setNewsRecyclerview()
                setSearchRecyclerview()

                setCenterProgressBar()

                setNewsSwipeRefresher()
                setNewsActivityClickableZones()

                setSearchSwipeRefresher()
                setSearchActivityClickableZones()
            }
            ActivityType.GALLERY -> {
                setGalleryRecyclerview()
                setGallerySwipeRefresher()
            }
            ActivityType.VR_MENU -> {
                setARMenuActivityClickableZones()
            }
            ActivityType.APP_MENU -> {

            }
        }
    }

    abstract fun setNewsActivityClickableZones()

    abstract fun setARMenuActivityClickableZones()

    abstract fun setSearchActivityClickableZones()

    fun setNewsAdapter(list: MutableList<NewsPost?>) {
        when (list.size) {
            0 -> {
                findViewById<TextView>(R.id.nothing_found).text =
                    R.string.noPostsFound.toString()
                findViewById<TextView>(R.id.nothing_found).visibility = View.VISIBLE
                newsRecyclerview.adapter = null
            }
            else -> {
                newsAdapter = RecyclerViewAdapter(list, this)
                newsRecyclerview.adapter = newsAdapter
                findViewById<TextView>(R.id.nothing_found).visibility = View.GONE
            }
        }
    }

    fun setSearchAdapter(list: MutableList<NewsPost?>) {
        when (list.size) {
            0 -> {
                findViewById<TextView>(R.id.nothing_found).also {
                    it.text = getText(R.string.noPostsFound)
                    it.visibility = View.VISIBLE
                }
                searchRecyclerview.adapter = null
            }
            else -> {
                searchSwipeRefresher.visibility = View.VISIBLE
                searchAdapter = RecyclerViewAdapter(list, this)
                searchRecyclerview.also {
                    it.visibility = View.VISIBLE
                    it.adapter = searchAdapter
                }
                findViewById<TextView>(R.id.nothing_found).visibility = View.GONE
            }
        }
    }

    fun setGalleryAdapter(list: MutableList<GalleryPhoto?>) {
        when (list.size) {
            0 -> {
                findViewById<TextView>(R.id.nothing_found).also {
                    it.text = getText(R.string.noPhotoFound)
                    it.visibility = View.VISIBLE
                }
                galleryRecyclerview?.adapter = null
            }
            else -> {
                galleryAdapter = GalleryRecyclerviewAdapter(list, applicationContext)
                galleryRecyclerview?.adapter = galleryAdapter
                findViewById<TextView>(R.id.nothing_found).visibility = View.GONE
            }
        }
    }

    private fun setCenterProgressBar() {
        if (!::centerProgressBar.isInitialized)
            centerProgressBar = findViewById(R.id.progress_bar)
    }

    private fun setNewsRecyclerview() {
        if (!::newsRecyclerview.isInitialized) {
            newsRecyclerview = findViewById(R.id.news_recyclerview)
            newsRecyclerview.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun setSearchRecyclerview() {
        if (!::searchRecyclerview.isInitialized) {
            searchRecyclerview = findViewById(R.id.search_recyclerview)
            searchRecyclerview.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun setGalleryRecyclerview() {
        if (galleryRecyclerview == null) {
            galleryRecyclerview = findViewById(R.id.gallery_recyclerview)
            galleryRecyclerview!!.layoutManager = GridLayoutManager(this, 1)
        }
    }

    private fun setNewsSwipeRefresher() {
        if (!::newsSwipeRefresher.isInitialized)
            newsSwipeRefresher = findViewById(R.id.news_swipe_refresher_including_recyclerview)
        newsSwipeRefresher.setProgressBackgroundColorSchemeColor(
            getResources().getColor(
                R.color.colorSpaceBar
            )
        )
        newsSwipeRefresher.setColorSchemeColors(getResources().getColor(R.color.colorC6F))
        newsSwipeRefresher.setOnRefreshListener {
            thread {
                tryToShowNews()
                newsSwipeRefresher.setRefreshing(false)
            }
        }
    }

    private fun setSearchSwipeRefresher() {
        if (!::searchSwipeRefresher.isInitialized)
            searchSwipeRefresher = findViewById(R.id.search_swipe_refresher_including_recyclerview)
        searchSwipeRefresher.also {
            it.setProgressBackgroundColorSchemeColor(
                getResources().getColor(
                    R.color.colorSpaceBar
                )
            )
            it.setColorSchemeColors(getResources().getColor(R.color.colorC6F))
            it.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
                thread {
                    handleSearchRequest()
                    it.setRefreshing(false)
                }
            })
        }
    }

    private fun setGallerySwipeRefresher() {
        /* if (gallerySwipeRefresher == null) {
            gallerySwipeRefresher = findViewById(R.id.gallery_swipe_refresher)
            gallerySwipeRefresher?.setProgressBackgroundColorSchemeColor(
                getResources().getColor(
                    R.color.colorSpaceBar
                )
            )
            gallerySwipeRefresher?.setColorSchemeColors(getResources().getColor(R.color.colorC6F))
            gallerySwipeRefresher?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
                thread {
                    tryToShowGallery()
                    gallerySwipeRefresher?.setRefreshing(false)
                }
            })
        } */
    }

    protected fun hideProgressBar() {
        runOnUiThread {
            centerProgressBar.visibility = View.GONE
        }
    }

    protected fun showProgressBar() {
        runOnUiThread {
            centerProgressBar.visibility = View.VISIBLE
        }
    }

    fun isNewsAdapterSet(): Boolean = newsAdapter != null

    fun isSearchAdapterSet(): Boolean = ::searchAdapter.isInitialized

    fun isGalleryAdapterSet(): Boolean = galleryAdapter != null

    abstract fun tryToShowNews()

    abstract fun tryToShowGallery()

    abstract fun tryToShowResults(userRequest: String)

    abstract fun handleSearchRequest()
}