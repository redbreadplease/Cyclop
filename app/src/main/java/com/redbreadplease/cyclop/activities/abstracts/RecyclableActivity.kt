package com.redbreadplease.cyclop.activities.abstracts

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.recyclerview.GalleryRecyclerViewAdapter
import com.redbreadplease.cyclop.recyclerview.PostsRecyclerViewAdapter
import com.redbreadplease.cyclop.retrofit.pojos.GalleryPhoto
import com.redbreadplease.cyclop.retrofit.pojos.NewsPost
import com.redbreadplease.cyclop.stuff.ActivityType
import kotlin.concurrent.thread

abstract class RecyclableActivity : BaseActivity() {
    protected lateinit var newsRecyclerView: RecyclerView
    private var newsAdapterPosts: PostsRecyclerViewAdapter? = null
    private lateinit var newsSwipeRefresher: SwipeRefreshLayout

    protected lateinit var searchRecyclerView: RecyclerView
    private lateinit var searchAdapterPosts: PostsRecyclerViewAdapter
    private lateinit var searchSwipeRefresher: SwipeRefreshLayout

    private lateinit var galleryRecyclerView: RecyclerView
    private var galleryAdapter: GalleryRecyclerViewAdapter? = null
    private lateinit var gallerySwipeRefresher: SwipeRefreshLayout

    private lateinit var centerProgressBar: ProgressBar


    fun setActivityView(activityType: ActivityType) {
        setNavbar()
        setActiveNavbarButton(activityType)
        when (activityType) {
            ActivityType.NEWS -> {
                setNewsRecyclerView()
                setSearchRecyclerView()

                setNewsSwipeRefresher()
                setSearchSwipeRefresher()

                setNewsActivityClickableZones()

                setCenterProgressBar()
            }
            ActivityType.GALLERY -> {
                setGalleryRecyclerView()

                setGallerySwipeRefresher()

                setCenterProgressBar()
            }
            ActivityType.VR_MENU -> {
                setARMenuActivityClickableZones()
            }
            ActivityType.APP_MENU -> {
            }
        }
    }

    private fun setNewsRecyclerView() {
        if (!::newsRecyclerView.isInitialized)
            newsRecyclerView = findViewById<RecyclerView>(R.id.news_recyclerview).apply {
                this.layoutManager = LinearLayoutManager(applicationContext)
            }
    }

    private fun setSearchRecyclerView() {
        if (!::searchRecyclerView.isInitialized)
            searchRecyclerView = findViewById<RecyclerView>(R.id.search_recyclerview).apply {
                this.layoutManager = LinearLayoutManager(applicationContext)
            }
    }

    private fun setGalleryRecyclerView() {
        if (!::galleryRecyclerView.isInitialized)
            galleryRecyclerView = findViewById<RecyclerView>(R.id.gallery_recyclerview).apply {
                this.layoutManager = GridLayoutManager(applicationContext, 1)
            }
    }

    private fun setNewsSwipeRefresher() {
        if (!::newsSwipeRefresher.isInitialized)
            newsSwipeRefresher =
                findViewById<SwipeRefreshLayout>(R.id.news_swipe_refresher_including_recyclerview).apply {
                    this.setProgressBackgroundColorSchemeColor(resources.getColor(R.color.colorSpaceBar))
                    this.setColorSchemeColors(resources.getColor(R.color.colorC6F))
                    this.setOnRefreshListener {
                        thread { tryToShowNews(); this.isRefreshing = false }
                    }
                }
    }

    private fun setSearchSwipeRefresher() {
        if (!::searchSwipeRefresher.isInitialized)
            searchSwipeRefresher =
                findViewById<SwipeRefreshLayout>(R.id.search_swipe_refresher_including_recyclerview).apply {
                    this.setProgressBackgroundColorSchemeColor(resources.getColor(R.color.colorSpaceBar))
                    this.setColorSchemeColors(resources.getColor(R.color.colorC6F))
                    this.setOnRefreshListener {
                        thread { handleSearchRequest(); this.isRefreshing = false }
                    }
                }
    }

    private fun setGallerySwipeRefresher() {
        if (!::gallerySwipeRefresher.isInitialized)
            gallerySwipeRefresher =
                findViewById<SwipeRefreshLayout>(R.id.gallery_swipe_refresher).apply {
                    this.setProgressBackgroundColorSchemeColor(resources.getColor(R.color.colorSpaceBar))
                    this.setColorSchemeColors(resources.getColor(R.color.colorC6F))
                    this.setOnRefreshListener {
                        thread { tryToShowGallery(); this.isRefreshing = false }
                    }
                }
    }

    abstract fun setNewsActivityClickableZones()

    abstract fun setARMenuActivityClickableZones()

    private fun setCenterProgressBar() {
        if (!::centerProgressBar.isInitialized)
            centerProgressBar = findViewById(R.id.progress_bar)
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

    fun setNewsAdapter(list: MutableList<NewsPost?>) {
        if (list.size == 0) {
            findViewById<TextView>(R.id.nothing_found).also {
                it.text = R.string.noPostsFound.toString()
                it.visibility = View.VISIBLE
            }
            newsRecyclerView.adapter = null
        } else {
            newsAdapterPosts = PostsRecyclerViewAdapter(list, this)
            newsRecyclerView.adapter = newsAdapterPosts
            findViewById<TextView>(R.id.nothing_found).visibility = View.GONE
        }
    }

    fun setSearchAdapter(list: MutableList<NewsPost?>) {
        if (list.size == 0) {
            findViewById<TextView>(R.id.nothing_found).also {
                it.text = getText(R.string.noPostsFound)
                it.visibility = View.VISIBLE
            }
            searchRecyclerView.adapter = null
        } else {
            searchSwipeRefresher.visibility = View.VISIBLE
            searchAdapterPosts = PostsRecyclerViewAdapter(list, this)
            searchRecyclerView.also {
                it.visibility = View.VISIBLE
                it.adapter = searchAdapterPosts
            }
            findViewById<TextView>(R.id.nothing_found).visibility = View.GONE
        }
    }

    fun setGalleryAdapter(list: MutableList<GalleryPhoto?>) {
        if (list.size == 0) {
            findViewById<TextView>(R.id.nothing_found).also {
                it.text = getText(R.string.noPhotoFound)
                it.visibility = View.VISIBLE
            }
            galleryRecyclerView.adapter = null
        } else {
            galleryAdapter = GalleryRecyclerViewAdapter(list, applicationContext)
            galleryRecyclerView.adapter = galleryAdapter
            findViewById<TextView>(R.id.nothing_found).visibility = View.GONE
        }
    }

    fun isNewsAdapterSet(): Boolean = newsAdapterPosts != null

    fun isSearchAdapterSet(): Boolean = ::searchAdapterPosts.isInitialized

    fun isGalleryAdapterSet(): Boolean = galleryAdapter != null

    abstract fun tryToShowNews()

    abstract fun tryToShowResults(userRequest: String)

    abstract fun tryToShowGallery()

    abstract fun handleSearchRequest()
}