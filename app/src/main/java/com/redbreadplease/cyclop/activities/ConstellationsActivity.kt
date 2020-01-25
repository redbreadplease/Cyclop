package com.redbreadplease.cyclop.activities

import android.os.Bundle
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.activities.abstracts.BaseActivity
import com.redbreadplease.cyclop.activities.enums.ButtonsPurposes

class ConstellationsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constellations)
        setupNavbar(ButtonsPurposes.CONSTELLATIONS)
    }
}
