package com.example.spacenews.activities.abstracts

import android.content.Intent
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.spacenews.R
import com.example.spacenews.activities.ConstellationsActivity
import com.example.spacenews.activities.NewsActivity
import com.example.spacenews.activities.PlanetActivity
import com.example.spacenews.activities.SearchActivity
import com.example.spacenews.activities.enums.ButtonsPurposes
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_news.*
import kotlin.concurrent.thread


abstract class BaseActivity : AppCompatActivity() {
    private var currentPageId: Int = -1

    fun setupNavbar(activeButtonPurpose: ButtonsPurposes) {
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
        bottom_navigation_view.menu[
                when (activeButtonPurpose) {
                    ButtonsPurposes.NEWS -> 0
                    ButtonsPurposes.SEARCH -> 1
                    ButtonsPurposes.PLANET -> 2
                    ButtonsPurposes.CONSTELLATIONS -> 3

                }
        ].icon = getDrawable(
            when (activeButtonPurpose) {
                ButtonsPurposes.NEWS -> R.drawable.ic_m_news_button_beige
                ButtonsPurposes.SEARCH -> R.drawable.ic_m_search_button_beige
                ButtonsPurposes.PLANET -> R.drawable.ic_m_planet_button_beige
                ButtonsPurposes.CONSTELLATIONS -> R.drawable.ic_m_constellations_button_beige
            }
        )
    }

    private fun buttonClicked(buttonsPurpose: ButtonsPurposes?) {
        when (buttonsPurpose) {
            ButtonsPurposes.NEWS -> {/*
                val options: ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    findViewById(R.id.toolbar),
                    this.getString(R.string.app_name)
                )
                val bundle: Bundle = options.toBundle()*/
                startActivity(Intent(this, NewsActivity::class.java))
            }
            ButtonsPurposes.SEARCH
            -> startActivity(Intent(this, SearchActivity::class.java))
            ButtonsPurposes.PLANET
            -> startActivity(Intent(this, PlanetActivity::class.java))
            ButtonsPurposes.CONSTELLATIONS
            -> startActivity(Intent(this, ConstellationsActivity::class.java))
            null -> println("Error: given null purpose")
        }
    }

    private fun getButtonPurposeById(id: Int): ButtonsPurposes? {
        var buttonPositionIndex: Int = -1
        for (i in 0 until bottom_navigation_view.menu.size())
            if (bottom_navigation_view.menu[i].itemId == id) {
                buttonPositionIndex = i
                break
            }
        return when (buttonPositionIndex) {
            0 -> ButtonsPurposes.NEWS
            1 -> ButtonsPurposes.SEARCH
            2 -> ButtonsPurposes.PLANET
            3 -> ButtonsPurposes.CONSTELLATIONS
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