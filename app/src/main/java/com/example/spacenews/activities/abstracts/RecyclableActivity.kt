package com.example.spacenews.activities.abstracts

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spacenews.R
import com.example.spacenews.recyclerview.RecyclerViewAdapter
import com.example.spacenews.retrofit.pojos.SpaceNewsPost

abstract class RecyclableActivity : BaseActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerViewAdapter? = null

    fun setAdapter(list: MutableList<SpaceNewsPost?>) {
        adapter = RecyclerViewAdapter(list)
        recyclerView?.adapter = adapter
    }

    fun setRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView?.layoutManager = LinearLayoutManager(this)
    }

    fun isAdapterSet(): Boolean = adapter == null
}