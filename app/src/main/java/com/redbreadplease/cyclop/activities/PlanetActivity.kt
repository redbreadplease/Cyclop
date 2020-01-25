package com.redbreadplease.cyclop.activities

import android.os.Bundle
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.activities.abstracts.BaseActivity
import com.redbreadplease.cyclop.activities.abstracts.RecyclableNetworkGalleryActivity
import com.redbreadplease.cyclop.activities.enums.ButtonsPurposes.PLANET

class PlanetActivity : RecyclableNetworkGalleryActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planet)
        setContentSpaceAndNavbar(PLANET)
        refreshContent()
    }

    override fun refreshContent() {
        tryToShowGallery()
    }
}