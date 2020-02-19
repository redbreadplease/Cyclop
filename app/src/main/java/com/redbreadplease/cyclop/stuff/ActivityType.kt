package com.redbreadplease.cyclop.stuff

import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.activities.AppMenuActivity
import com.redbreadplease.cyclop.activities.GalleryActivity
import com.redbreadplease.cyclop.activities.NewsActivity
import com.redbreadplease.cyclop.activities.VRMenuActivity

enum class ActivityType {
    NEWS, GALLERY, VR_MENU, APP_MENU
}

fun getDrawableIdBy(activityType: ActivityType) = when (activityType) {
    ActivityType.NEWS -> R.drawable.ic_m_news_button_white
    ActivityType.GALLERY -> R.drawable.ic_m_planet_button_white
    ActivityType.VR_MENU -> R.drawable.ic_m_constellations_button_white
    ActivityType.APP_MENU -> R.drawable.ic_m_menu_button_white
}

fun getNavbarMenuItemIndexBy(activityType: ActivityType) = when (activityType) {
    ActivityType.NEWS -> 0
    ActivityType.GALLERY -> 1
    ActivityType.VR_MENU -> 2
    ActivityType.APP_MENU -> 3
}

fun getButtonPurposeTypeBy(navbarMenuButtonClickedIndex: Int) =
    when (navbarMenuButtonClickedIndex) {
        0 -> ActivityType.NEWS
        1 -> ActivityType.GALLERY
        2 -> ActivityType.VR_MENU
        3 -> ActivityType.APP_MENU
        else -> null
    }

fun getActivityClassJavaBy(activityType: ActivityType) = when (activityType) {
    ActivityType.NEWS -> NewsActivity::class.java
    ActivityType.GALLERY -> GalleryActivity::class.java
    ActivityType.VR_MENU -> VRMenuActivity::class.java
    ActivityType.APP_MENU -> AppMenuActivity::class.java
}
