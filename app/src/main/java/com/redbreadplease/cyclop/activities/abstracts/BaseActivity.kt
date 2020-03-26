package com.redbreadplease.cyclop.activities.abstracts

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.redbreadplease.cyclop.stuff.*
import kotlinx.android.synthetic.main.navbar.*
import kotlin.concurrent.thread


abstract class BaseActivity : AppCompatActivity() {
    private var currentPageId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    fun setNavbar() {
        bottom_navigation_view.also {
            it.setTextVisibility(false)
            it.enableAnimation(false)
            for (i in 0 until it.menu.size())
                it.setIconTintList(i, null)
            it.setOnNavigationItemSelectedListener {
                when (currentPageId) {
                    it.itemId -> false
                    else -> {
                        currentPageId = it.itemId
                        thread {
                            buttonClicked(getButtonPurposeById(currentPageId))
                        }
                        true
                    }
                }
            }
        }
    }

    fun setActiveNavbarButton(activityNow: ActivityType) {
        bottom_navigation_view.menu[getNavbarMenuItemIndexBy(activityNow)].also {
            it.setEnabled(false)
            it.icon = getDrawable(getDrawableIdBy(activityNow))
        }
    }

    private fun buttonClicked(buttonsPurpose: ActivityType?) {
        startActivity(Intent(this, getActivityClassJavaBy(buttonsPurpose!!)))
    }

    private fun getButtonPurposeById(id: Int): ActivityType? {
        var buttonPositionIndex: Int = -1
        for (i in 0 until bottom_navigation_view.menu.size())
            if (bottom_navigation_view.menu[i].itemId == id) {
                buttonPositionIndex = i
                break
            }
        return getButtonPurposeTypeBy(buttonPositionIndex)
    }

    fun createToast(notificationText: String) {
        runOnUiThread {
            Toast.makeText(
                applicationContext,
                notificationText, Toast.LENGTH_SHORT
            ).show()
        }
    }

    abstract fun prepareActivityView()
}