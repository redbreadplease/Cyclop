package com.example.spacenews.activities

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.spacenews.R
import com.example.spacenews.abstracts.BaseActivity
import com.example.spacenews.enums.ButtonsPurposes
import kotlinx.android.synthetic.main.activity_planet.*

class PlanetActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        prepareActivityView()
        setupBottomNavbar()
    }

    override fun prepareActivityView() {
        setSelectedItemMenuIcon(ButtonsPurposes.PLANET)
    }
}