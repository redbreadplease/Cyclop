package com.redbreadplease.cyclop.activities.abstracts

import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.activities.enums.ButtonsPurposes
import com.redbreadplease.cyclop.recyclerview.GalleryRecyclerviewAdapter
import com.redbreadplease.cyclop.retrofit.pojos.GalleryPhoto
import java.lang.NullPointerException
import java.lang.reflect.Executable
import kotlin.concurrent.thread

abstract class RecyclableGalleryActivity : BaseActivity() {
    private var galleryRecyclerview: RecyclerView? = null
    private var adapter: GalleryRecyclerviewAdapter? = null
    var swipeRefresher: SwipeRefreshLayout? = null

    fun setContentSpaceAndNavbar(activeButtonPurpose: ButtonsPurposes) {
        setupNavbar(activeButtonPurpose)
        setRecyclerView()
        setSwipeRefresher()
    }

    fun setAdapter(list: MutableList<GalleryPhoto?>) {
        when (list.size) {
            0 -> {
                findViewById<TextView>(R.id.no_photo_found_text_view).setText(R.string.noPhotoFound)
                galleryRecyclerview?.adapter = null
            }
            else -> {
                adapter = GalleryRecyclerviewAdapter(list, applicationContext)
                try {
                    galleryRecyclerview?.adapter = adapter
                } catch (e: NullPointerException) {

                }
                findViewById<TextView>(R.id.no_photo_found_text_view).setText("")
            }
        }
    }

    private fun setRecyclerView() {
        if (galleryRecyclerview == null) {
            galleryRecyclerview = findViewById(R.id.gallery_recyclerview)
            galleryRecyclerview?.layoutManager = GridLayoutManager(this, 1)
        }
    }

    private fun setSwipeRefresher() {
        if (swipeRefresher == null) {
            swipeRefresher = findViewById(R.id.gallery_swipe_refresher)
            swipeRefresher?.setProgressBackgroundColorSchemeColor(
                getResources().getColor(
                    R.color.colorSpaceBar
                )
            )
            swipeRefresher?.setColorSchemeColors(getResources().getColor(R.color.colorC6F))
            swipeRefresher?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
                thread {
                    refreshContent()
                    swipeRefresher?.setRefreshing(false)
                }
            })
        }
    }

    abstract fun refreshContent()

    fun isAdapterSet(): Boolean = adapter != null
}