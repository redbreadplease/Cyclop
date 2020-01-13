package com.example.spacenews.activities

import android.os.Bundle
import com.example.spacenews.R
import com.example.spacenews.activities.abstracts.RecyclableNewsActivity
import com.example.spacenews.activities.enums.ButtonsPurposes.NEWS
import kotlin.concurrent.thread

class NewsActivity : RecyclableNewsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        setContentSpaceAndNavbar(NEWS)
        refreshContent()
    }

    override fun refreshContent() {
        tryToShowNews()
    }
}
