package com.redbreadplease.cyclop.activities

import android.os.Bundle
import android.widget.EditText
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.activities.abstracts.RecyclableSearchPostsActivity
import com.redbreadplease.cyclop.activities.enums.ButtonsPurposes.SEARCH
import kotlin.concurrent.thread

class SearchPostsActivity : RecyclableSearchPostsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setThisActivityClickableZones()
        setContentSpaceAndNavbar(SEARCH)
    }

    override fun refreshContent() {
        handleSearchRequest(
            findViewById<EditText>(R.id.search_text_request_frame).getText().toString()
        )
    }

    override fun handleSearchRequest(requestBody: String) {
        if (requestBody != "")
            thread {
                tryShowResults(requestBody)
            }
    }
}
