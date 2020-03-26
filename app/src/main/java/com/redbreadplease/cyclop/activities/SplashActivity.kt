package com.redbreadplease.cyclop.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread


class SplashActivity : AppCompatActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thread { this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }
        val intent = Intent(this, DebugActivity::class.java)
        startActivity(intent)
        finish()
    }
}