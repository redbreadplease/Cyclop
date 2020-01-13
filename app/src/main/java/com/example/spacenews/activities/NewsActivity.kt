package com.example.spacenews.activities

import android.os.Bundle
import com.example.spacenews.R
import com.example.spacenews.activities.abstracts.RecyclableNetworkActivity
import com.example.spacenews.activities.enums.ButtonsPurposes.NEWS

class NewsActivity : RecyclableNetworkActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        setActivityView(NEWS)
        tryToShowNews()
    }
}
