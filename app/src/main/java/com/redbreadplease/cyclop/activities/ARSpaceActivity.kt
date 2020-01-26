package com.redbreadplease.cyclop.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.redbreadplease.cyclop.R
import kotlin.concurrent.thread

class ARSpaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar_space)
        prepareActivityView()
    }

    private fun prepareActivityView() {
        thread {
            setButtonsTouchable()
        }
    }

    private fun setButtonsTouchable() {
        findViewById<Button>(R.id.exit_ar_button).setOnClickListener {
            startActivity(Intent(this, ARMenuActivity::class.java))
        }
    }

    private fun createToast(notificationText: String) {
        thread {
            Toast.makeText(
                getApplicationContext(),
                notificationText, Toast.LENGTH_SHORT
            ).show()
        }
    }
}