package com.example.spacenews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spacenews.recyclerview.RecyclerViewAdapter
import com.example.spacenews.retrofit.pojos.SpaceNewsPost
import com.example.spacenews.retrofit.NetworkService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tryToShowNews()

        tuneBottomNavbar()
    }

    private fun tryToShowNews() {
        thread {
            setRecyclerView()
            showNewsTitles()
            if (adapter == null)
                showNewsTitles()
        }
    }

    private fun tuneBottomNavbar() {
        bottom_navigation_view.setTextVisibility(false)
        bottom_navigation_view.enableItemShiftingMode(false)
        bottom_navigation_view.enableShiftingMode(false)
        bottom_navigation_view.enableAnimation(false)
        for (i in 0 until bottom_navigation_view.menu.size())
            bottom_navigation_view.setIconTintList(i, null)
    }

    private fun setAdapter(list: MutableList<String>) {
        adapter = RecyclerViewAdapter(list)
        recyclerView?.adapter = adapter
    }

    private fun setRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView?.layoutManager = LinearLayoutManager(this)
    }

    private fun showNewsTitles() {
        NetworkService.getInstance()
            .getJSONApi()
            ?.getPosts()
            ?.enqueue(object : Callback<List<SpaceNewsPost?>> {
                override fun onResponse(
                    call: Call<List<SpaceNewsPost?>>,
                    response: Response<List<SpaceNewsPost?>>
                ) {
                    println("!!!!It\'s all ok!")
                    val posts: List<SpaceNewsPost?>? = response.body()
                    val content = mutableListOf<String>()
                    if (posts != null)
                        for (post: SpaceNewsPost? in posts)
                            content.add(post?.title.toString())
                    setAdapter(content)
                }

                override fun onFailure(call: Call<List<SpaceNewsPost?>>, t: Throwable) {
                    println("!!!!Something has gone wrong")
                    t.printStackTrace()
                }
            })
    }
}
