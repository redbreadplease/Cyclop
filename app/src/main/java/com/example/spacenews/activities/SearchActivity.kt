package com.example.spacenews.activities

import android.os.Bundle
import android.widget.EditText
import com.example.spacenews.R
import com.example.spacenews.activities.abstracts.RecyclableSearchActivity
import com.example.spacenews.activities.enums.ButtonsPurposes.SEARCH
import kotlin.concurrent.thread

class SearchActivity : RecyclableSearchActivity() {
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
