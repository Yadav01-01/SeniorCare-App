package com.bussiness.seniorcareapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.clRoot) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val onboardingItems = listOf(
            OnboardingItem(R.drawable.onb_img1, "LOREM IPSUM", "Lorem Ipsum is simply dummy text of the printing and typesetting industry"),
            OnboardingItem(R.drawable.onb_img1, "LOREM IPSUM", "Lorem Ipsum is simply dummy text of the printing and typesetting industry"),
            OnboardingItem(R.drawable.onb_img1, "LOREM IPSUM", "Lorem Ipsum is simply dummy text of the printing and typesetting industry"),
        )

        viewPager = binding.viewPager
        tabIndicator = binding.tabIndicator

        viewPager.adapter = OnboardingAdapter(onboardingItems){
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }

        TabLayoutMediator(tabIndicator, viewPager) { tab, _ ->
            tab.setCustomView(R.layout.custom_tab)
        }.attach()

        updateTabDots(0) // Set initial selected dot


        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateTabDots(position)
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
}
