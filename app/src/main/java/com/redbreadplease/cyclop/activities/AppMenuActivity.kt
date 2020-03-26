package com.redbreadplease.cyclop.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.activities.abstracts.BaseActivity
import com.redbreadplease.cyclop.stuff.ActivityType
import kotlin.concurrent.thread

class AppMenuActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_menu)
        prepareActivityView()
    }

    override fun prepareActivityView() {
        thread {
            setNavbar()
            setActiveNavbarButton(ActivityType.APP_MENU)
        }
    }

    fun startDebugActivity(view: View) {
        startActivity(Intent(this, DebugActivity::class.java))
    }
}