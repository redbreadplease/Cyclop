package com.redbreadplease.cyclop.activities.abstracts

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.activities.enums.ButtonsPurposes
import com.redbreadplease.cyclop.recyclerview.RecyclerViewAdapter
import com.redbreadplease.cyclop.retrofit.pojos.SpaceNewsPost
import kotlin.concurrent.thread

abstract class RecyclableActivity : BaseActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerViewAdapter? = null
    var swipeRefresher: SwipeRefreshLayout? = null

    fun setContentSpaceAndNavbar(activeButtonPurpose: ButtonsPurposes) {
        setupNavbar(activeButtonPurpose)
        setRecyclerView()
        setSwipeRefresher()
    }

    fun setAdapter(list: MutableList<SpaceNewsPost?>) {
        when (list.size) {
            0 -> {
                findViewById<TextView>(R.id.no_posts_found_text_view).setText(R.string.noPostsFound)
                recyclerView?.adapter = null
            }
            else -> {
                adapter = RecyclerViewAdapter(list, applicationContext)
                recyclerView?.adapter = adapter
                findViewById<TextView>(R.id.no_posts_found_text_view).setText("")
            }
        }
    }

    private fun setRecyclerView() {
        if (recyclerView == null) {
            recyclerView = findViewById(R.id.recyclerview)
            recyclerView?.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun setSwipeRefresher() {
        if (swipeRefresher == null) {
            swipeRefresher = findViewById(R.id.swipe_refresher)
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