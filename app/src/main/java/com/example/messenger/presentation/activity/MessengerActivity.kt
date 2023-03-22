package com.example.messenger.presentation.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import com.example.messenger.R
import com.example.messenger.databinding.ActivityMessengerBinding

class MessengerActivity : AppCompatActivity() {
    private var _binding: ActivityMessengerBinding? = null
    private val binding get() = _binding!!
    private var backPressed = false

    private fun initSystemBar() {
        window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT

        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initSystemBar()
        super.onCreate(savedInstanceState)
        _binding = ActivityMessengerBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (findNavController(R.id.messenger_navigation_fragment).navigateUp().not()) {
            if (backPressed) {
                backPressed = false
                finishAffinity()
            } else {
                backPressed = true

                Handler().postDelayed(Runnable {
                    backPressed = false
                }, 3000)

                Toast
                    .makeText(this, "Press again to exit", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}