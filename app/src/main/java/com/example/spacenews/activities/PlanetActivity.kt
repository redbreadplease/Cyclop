package com.example.spacenews.activities

import android.os.Bundle
import com.example.spacenews.R
import com.example.spacenews.activities.abstracts.BaseActivity
import com.example.spacenews.activities.enums.ButtonsPurposes.PLANET

class PlanetActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setActivityView(PLANET)
    }
}