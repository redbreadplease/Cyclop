package com.redbreadplease.cyclop.activities

import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.redbreadplease.cyclop.AR.ARStars


class ARSpaceActivity : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize(ARStars(), AndroidApplicationConfiguration())
    }
}

/*
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
 */