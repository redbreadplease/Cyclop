package com.example.spacenews.abstracts

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spacenews.R
import com.example.spacenews.recyclerview.RecyclerViewAdapter

abstract class RecyclableActivity : BaseActivity() {
    var recyclerView: RecyclerView? = null
    var adapter: RecyclerViewAdapter? = null

    fun setAdapter(list: MutableList<String>) {
        adapter = RecyclerViewAdapter(list)
        recyclerView?.adapter = adapter
    }

    fun setRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView?.layoutManager = LinearLayoutManager(this)
    }
}