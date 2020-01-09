package com.example.spacenews.activities

import android.os.Bundle
import com.example.spacenews.R
import com.example.spacenews.abstracts.RecyclableNetworkActivity

class NewsActivity : RecyclableNetworkActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_space_news)
        prepareActivityView()
        setupBottomNavbar()
        tryToShowNews()
    }
}
