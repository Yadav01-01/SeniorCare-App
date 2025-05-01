package com.bussiness.seniorcareapp.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.bussiness.seniorcareapp.databinding.ActivitySplashBinding
import kotlin.math.hypot

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private var rippleCount = 0
    private val rippleRepeat = 3
    private val rippleDuration = 1000L 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.main.post {
            startRippleAnimation(binding.main)
        }
    }

    private fun startRippleAnimation(view: View) {
        val cx = view.width / 2
        val cy = view.height / 2
        val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius)
        anim.duration = rippleDuration

        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                rippleCount++
                if (rippleCount < rippleRepeat) {
                    // Reset and repeat ripple
                    view.postDelayed({
                        startRippleAnimation(view)
                    }, 200)
                } else {
                    // After 3 ripples, go to Onboarding
                    val intent = Intent(this@SplashActivity, OnboardingActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        })

        anim.start()
    }
}
