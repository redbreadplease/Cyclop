package com.redbreadplease.cyclop.abstracts

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.enums.ActivityType
import com.redbreadplease.cyclop.recyclerview.GalleryRecyclerviewAdapter
import com.redbreadplease.cyclop.recyclerview.RecyclerViewAdapter
import com.redbreadplease.cyclop.retrofit.pojos.GalleryPhoto
import com.redbreadplease.cyclop.retrofit.pojos.NewsPost
import pl.droidsonroids.gif.GifImageView
import kotlin.concurrent.thread

abstract class RecyclableActivity : BaseActivity() {
    private var galleryRecyclerview: RecyclerView? = null
    private var galleryAdapter: GalleryRecyclerviewAdapter? = null
    var gallerySwipeRefresher: SwipeRefreshLayout? = null
    private var newsRecyclerview: RecyclerView? = null
    private var newsAdapter: RecyclerViewAdapter? = null
    var newsSwipeRefresher: SwipeRefreshLayout? = null

    fun setActivityView(activityType: ActivityType) {
        setNavbar()
        setActiveNavbarButton(activityType)
        when (activityType) {
            ActivityType.NEWS -> {
                setNewsRecyclerview()
                setNewsSwipeRefresher()
            }
            ActivityType.SEARCH -> {
                setNewsRecyclerview()
                setSearchSwipeRefresher()
                setSearchActivityClickableZones()
            }
            ActivityType.PLANET -> {
                setGalleryRecyclerview()
                setGallerySwipeRefresher()
            }
            ActivityType.CONSTELLATIONS -> {
                setARMenuActivityClickableZones()
            }
        }
    }

    abstract fun setARMenuActivityClickableZones()

    abstract fun setSearchActivityClickableZones()

    fun setNewsAdapter(list: MutableList<NewsPost?>) {
        findViewById<GifImageView>(R.id.loading_gif).setVisibility(View.INVISIBLE)
        when (list.size) {
            0 -> {
                findViewById<TextView>(R.id.no_posts_found_text_view).setVisibility(View.VISIBLE)
                newsRecyclerview?.adapter = null
            }
            else -> {
                newsAdapter = RecyclerViewAdapter(list, applicationContext)
                newsRecyclerview?.adapter = newsAdapter
                findViewById<TextView>(R.id.no_posts_found_text_view).setVisibility(View.INVISIBLE)
            }
        }
    }

    fun setGalleryAdapter(list: MutableList<GalleryPhoto?>) {
        findViewById<GifImageView>(R.id.loading_gif).setVisibility(View.INVISIBLE)
        when (list.size) {
            0 -> {
                findViewById<TextView>(R.id.no_photo_found_text_view).setVisibility(View.VISIBLE)
                galleryRecyclerview?.adapter = null
            }
            else -> {
                galleryAdapter = GalleryRecyclerviewAdapter(list, applicationContext)
                galleryRecyclerview?.adapter = galleryAdapter
                findViewById<TextView>(R.id.no_photo_found_text_view).setVisibility(View.INVISIBLE)
            }
        }
    }

    private fun setNewsRecyclerview() {
        if (newsRecyclerview == null) {
            newsRecyclerview = findViewById(R.id.recyclerview)
            newsRecyclerview?.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun setGalleryRecyclerview() {
        if (galleryRecyclerview == null) {
            galleryRecyclerview = findViewById(R.id.gallery_recyclerview)
            galleryRecyclerview?.layoutManager = GridLayoutManager(this, 1)
        }
    }

    private fun setNewsSwipeRefresher() {
        if (newsSwipeRefresher == null) {
            newsSwipeRefresher = findViewById(R.id.swipe_refresher)
            newsSwipeRefresher?.setProgressBackgroundColorSchemeColor(
                getResources().getColor(
                    R.color.colorSpaceBar
                )
            )
            newsSwipeRefresher?.setColorSchemeColors(getResources().getColor(R.color.colorC6F))
            newsSwipeRefresher?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
                thread {
                    tryToShowNews()
                    newsSwipeRefresher?.setRefreshing(false)
                }
            })
        }
    }

    private fun setSearchSwipeRefresher() {
        if (newsSwipeRefresher == null) {
            newsSwipeRefresher = findViewById(R.id.swipe_refresher)
            newsSwipeRefresher?.setProgressBackgroundColorSchemeColor(
                getResources().getColor(
                    R.color.colorSpaceBar
                )
            )
            newsSwipeRefresher?.setColorSchemeColors(getResources().getColor(R.color.colorC6F))
            newsSwipeRefresher?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
                thread {
                    handleSearchRequest()
                    newsSwipeRefresher?.setRefreshing(false)
                }
            })
        }
    }

    private fun setGallerySwipeRefresher() {
        if (gallerySwipeRefresher == null) {
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
        }
    }

    fun isNewsAdapterSet(): Boolean = newsAdapter != null

    fun isGalleryAdapterSet(): Boolean = galleryAdapter != null

    abstract fun tryToShowNews()

    abstract fun tryToShowGallery()

    abstract fun tryToShowResults(userRequest: String)

    abstract fun handleSearchRequest()
}