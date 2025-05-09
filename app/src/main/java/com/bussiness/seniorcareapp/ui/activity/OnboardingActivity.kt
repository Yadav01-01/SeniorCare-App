package com.bussiness.seniorcareapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.data.model.OnboardingItem
import com.bussiness.seniorcareapp.databinding.ActivityOnboardingBinding
import com.bussiness.seniorcareapp.ui.adapter.OnboardingAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabIndicator: TabLayout
    private lateinit var indicatorLayout: LinearLayout
    private val totalSteps = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize indicatorLayout from binding
        indicatorLayout = binding.indicatorLayout

        ViewCompat.setOnApplyWindowInsetsListener(binding.clRoot) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val onboardingItems = listOf(
            OnboardingItem(R.drawable.onb_img1, "LOREM IPSUM", "Lorem Ipsum is simply dummy text of the printing and typesetting industry"),
            OnboardingItem(R.drawable.onb_img2, "LOREM IPSUM", "Lorem Ipsum is simply dummy text of the printing and typesetting industry"),
            OnboardingItem(R.drawable.onb_img3, "LOREM IPSUM", "Lorem Ipsum is simply dummy text of the printing and typesetting industry")
        )

        viewPager = binding.viewPager
        tabIndicator = binding.tabIndicator

        viewPager.adapter = OnboardingAdapter(
            onboardingItems,
            onSkipClick = {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            },
            onNextClick = { position ->
                if (position < onboardingItems.lastIndex) {
                    viewPager.currentItem = position + 1
                } else {
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        )

        // Setup TabLayout with custom dot indicator
        TabLayoutMediator(tabIndicator, viewPager) { tab, _ ->
            tab.setCustomView(R.layout.custom_tab)
        }.attach()

        updateTabDots(0) // Set initial selected dot
        setupIndicators()

        // Register only one PageChangeCallback
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateTabDots(position) // Update TabLayout dots
                updateIndicators(position) // Update custom indicators
            }
        })
    }

    private fun updateTabDots(selectedPosition: Int) {
        for (i in 0 until tabIndicator.tabCount) {
            val tab = tabIndicator.getTabAt(i)
            val imageView = tab?.customView?.findViewById<ImageView>(R.id.tabDot)
            if (i == selectedPosition) {
                imageView?.setImageResource(R.drawable.tab_ic) // selected
            } else {
                imageView?.setImageResource(R.drawable.non_selected_tab_ic) // non-selected
            }
        }
    }

    private fun setupIndicators() {
        indicatorLayout.removeAllViews()
        for (i in 0 until totalSteps) {
            val view = LayoutInflater.from(this)
                .inflate(R.layout.item_indicator_dot, indicatorLayout, false)

            val dotImage = view.findViewById<ImageView>(R.id.dotImage)
            dotImage.setImageResource(if (i == 0) R.drawable.selected_ic_home else R.drawable.ic_home)

            val dotLine = view.findViewById<View>(R.id.dot)
            dotLine.visibility = if (i < 2) View.VISIBLE else View.INVISIBLE


            indicatorLayout.addView(view)
        }
    }

    private fun updateIndicators(position: Int) {
        for (i in 0 until indicatorLayout.childCount) {
            val dot = indicatorLayout.getChildAt(i).findViewById<ImageView>(R.id.dotImage)
            dot.setImageResource(if (i == position) R.drawable.selected_ic_home else R.drawable.ic_home)
        }
    }
}

