package com.redbreadplease.cyclop.activities

import android.content.Intent
import android.os.Bundle
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.activities.abstracts.NetworkRecyclableActivity
import kotlin.concurrent.thread


class LoadingActivity : NetworkRecyclableActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_application)
        prepareActivityView()
    }

    override fun prepareActivityView() {
        thread {
            Thread.sleep(400)
            startActivity(Intent(this, NewsActivity::class.java))
        }
    }
}