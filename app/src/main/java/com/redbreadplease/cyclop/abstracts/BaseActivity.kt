package com.redbreadplease.cyclop.abstracts

import android.content.Intent
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.enums.ActivityType
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.redbreadplease.cyclop.activities.ARMenuActivity
import com.redbreadplease.cyclop.activities.GalleryActivity
import com.redbreadplease.cyclop.activities.NewsActivity
import com.redbreadplease.cyclop.activities.SearchActivity
import kotlinx.android.synthetic.main.activity_news.*
import kotlin.concurrent.thread


abstract class BaseActivity : AppCompatActivity() {
    private var currentPageId: Int = -1

    fun setNavbar() {
        bottom_navigation_view.setTextVisibility(false)
        bottom_navigation_view.enableAnimation(false)
        for (i in 0 until bottom_navigation_view.menu.size())
            bottom_navigation_view.setIconTintList(i, null)
        bottom_navigation_view.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (currentPageId) {
                    item.getItemId() -> return false
                    else -> {
                        currentPageId = item.getItemId()
                        thread {
                            buttonClicked(getButtonPurposeById(currentPageId))
                        }
                        return true
                    }
                }
            }
        })

    }

    fun setActiveNavbarButton(activityNow: ActivityType) {
        bottom_navigation_view.menu[
                when (activityNow) {
                    ActivityType.NEWS -> 0
                    ActivityType.SEARCH -> 1
                    ActivityType.PLANET -> 2
                    ActivityType.CONSTELLATIONS -> 3

                }
        ].icon = getDrawable(
            when (activityNow) {
                ActivityType.NEWS -> R.drawable.ic_m_news_button_beige
                ActivityType.SEARCH -> R.drawable.ic_m_search_button_beige
                ActivityType.PLANET -> R.drawable.ic_m_planet_button_beige
                ActivityType.CONSTELLATIONS -> R.drawable.ic_m_constellations_button_beige
            }
        )
    }

    private fun buttonClicked(buttonsPurpose: ActivityType?) {
        when (buttonsPurpose) {
            ActivityType.NEWS ->
                startActivity(Intent(this, NewsActivity::class.java))
            ActivityType.SEARCH ->
                startActivity(Intent(this, SearchActivity::class.java))
            ActivityType.PLANET ->
                startActivity(Intent(this, GalleryActivity::class.java))
            ActivityType.CONSTELLATIONS ->
                startActivity(Intent(this, ARMenuActivity::class.java))
            null -> println("Error: given null purpose")
        }
    }

    private fun getButtonPurposeById(id: Int): ActivityType? {
        var buttonPositionIndex: Int = -1
        for (i in 0 until bottom_navigation_view.menu.size())
            if (bottom_navigation_view.menu[i].itemId == id) {
                buttonPositionIndex = i
                break
            }
        return when (buttonPositionIndex) {
            0 -> ActivityType.NEWS
            1 -> ActivityType.SEARCH
            2 -> ActivityType.PLANET
            3 -> ActivityType.CONSTELLATIONS
            else -> null
        }
    }

    fun createToast(notificationText: String) {
        Toast.makeText(
            getApplicationContext(),
            notificationText, Toast.LENGTH_SHORT
        ).show()
    }
}