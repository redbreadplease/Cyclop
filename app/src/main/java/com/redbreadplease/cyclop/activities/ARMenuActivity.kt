package com.redbreadplease.cyclop.activities

import android.os.Bundle
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.activities.abstracts.NetworkRecyclableActivity
import com.redbreadplease.cyclop.stuff.ActivityType
import kotlin.concurrent.thread

class ARMenuActivity : NetworkRecyclableActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar_menu)
        prepareActivityView()
    }

    override fun prepareActivityView() {
        thread {
            setActivityView(ActivityType.CONSTELLATIONS)
            setARMenuActivityClickableZones()
        }
    }
}