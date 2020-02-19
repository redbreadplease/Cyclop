package com.redbreadplease.cyclop.activities

import android.os.Bundle
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
}