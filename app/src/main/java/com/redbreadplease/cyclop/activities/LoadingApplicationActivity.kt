package com.redbreadplease.cyclop.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.redbreadplease.cyclop.R
import kotlin.concurrent.thread


class LoadingApplicationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_application)
        thread {
            Thread.sleep(400)
            startActivity(Intent(this, NewsPostsActivity::class.java))
        }
    }
}
