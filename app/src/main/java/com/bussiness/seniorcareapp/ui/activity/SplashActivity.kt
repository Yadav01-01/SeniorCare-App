package com.bussiness.seniorcareapp.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.bussiness.seniorcareapp.databinding.ActivitySplashBinding
import com.bussiness.seniorcareapp.utils.SessionManager

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var sessionManager: SessionManager

    private var rippleCount = 0
    private val rippleRepeat = 3
    private val rippleDuration = 800L
    private val splashDelay = 3000L // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        startLogoRippleEffect()

        // Delay navigation after 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNextScreen()
        }, splashDelay)
    }

    private fun startLogoRippleEffect() {
        val rippleView = binding.rippleView
        rippleView.visibility = View.VISIBLE
        rippleView.scaleX = 0.5f
        rippleView.scaleY = 0.5f
        rippleView.alpha = 1f

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(rippleView, View.SCALE_X, 0.5f, 2f),
            ObjectAnimator.ofFloat(rippleView, View.SCALE_Y, 0.5f, 2f),
            ObjectAnimator.ofFloat(rippleView, View.ALPHA, 1f, 0f)
        )
        animatorSet.duration = rippleDuration
        animatorSet.interpolator = AccelerateDecelerateInterpolator()

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                rippleCount++
                if (rippleCount < rippleRepeat) {
                    startLogoRippleEffect()
                }
            }
        })
        animatorSet.start()
    }

    private fun navigateToNextScreen() {
        val intent = if (sessionManager.isLoggedIn()) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, OnboardingActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
