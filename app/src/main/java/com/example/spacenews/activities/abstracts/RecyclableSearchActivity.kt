package com.example.spacenews.activities.abstracts

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.spacenews.R
import com.example.spacenews.retrofit.NetworkService
import com.example.spacenews.retrofit.pojos.SpaceNewsPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


abstract class RecyclableSearchActivity : RecyclableActivity() {
    var searchButton: Button? = null

    fun tryShowResults(userRequest: String) {
        showFilteredPosts(userRequest)
        Thread.sleep(100)
        if (!isAdapterSet())
            showFilteredPosts(userRequest)
    }

    private fun showFilteredPosts(userRequest: String) {
        NetworkService.getInstance()
            .getJSONApi()
            ?.getFilteredPosts(userRequest)
            ?.enqueue(object : Callback<List<SpaceNewsPost?>> {
                override fun onResponse(
                    call: Call<List<SpaceNewsPost?>>,
                    response: Response<List<SpaceNewsPost?>>
                ) {
                    println("!!!!It\'s all ok!")
                    val posts: List<SpaceNewsPost?>? = response.body()
                    val content = mutableListOf<SpaceNewsPost?>()
                    if (posts != null)
                        for (post: SpaceNewsPost? in posts)
                            content.add(post)
                    setAdapter(content)
                }

                override fun onFailure(call: Call<List<SpaceNewsPost?>>, t: Throwable) {
                    println("!!!!Something has gone wrong")
                    t.printStackTrace()
                    Toast.makeText(
                        getApplicationContext(),
                        "Error while load. Try refreshing page", Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    fun setThisActivityClickableZones() {
        if (searchButton == null) {
            searchButton = findViewById(R.id.search_posts_button)
            searchButton?.setOnClickListener {
                try {
                    val imm =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                } catch (e: Exception) { // TODO: handle exception
                }

                val searchFieldText: String =
                    findViewById<EditText>(R.id.search_text_request_frame).getText().toString()
                tryShowResults(searchFieldText)
            }

        }
    }

    abstract fun handleSearchRequest(requestBody: String)
}