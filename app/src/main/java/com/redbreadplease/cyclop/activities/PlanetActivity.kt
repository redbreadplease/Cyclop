package com.redbreadplease.cyclop.activities

import android.os.Bundle
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.activities.abstracts.BaseActivity
import com.redbreadplease.cyclop.activities.enums.ButtonsPurposes.PLANET

class PlanetActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planet)
        setupNavbar(PLANET)

        //val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        //val recyclerView =
        //    findViewById<View>(R.id.rv_images) as RecyclerView
        //recyclerView.setHasFixedSize(true)
        //recyclerView.layoutManager = layoutManager
        //val adapter = ImageGalleryAdapter(this, GalleryPhoto.getSpacePhotos())
        //recyclerView.adapter = adapter
    }
}