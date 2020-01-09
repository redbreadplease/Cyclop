package com.example.spacenews.activities

import android.os.Bundle
import com.example.spacenews.R
import com.example.spacenews.abstracts.BaseActivity
import com.example.spacenews.enums.ButtonsPurposes

class ConstellationsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constellations)
        prepareActivityView()
        setupBottomNavbar()
    }

    override fun prepareActivityView() {
        setSelectedItemMenuIcon(ButtonsPurposes.CONSTELLATIONS)
    }
}
