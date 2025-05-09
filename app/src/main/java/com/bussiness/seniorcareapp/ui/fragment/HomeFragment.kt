package com.bussiness.seniorcareapp.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.data.model.BannerData
import com.bussiness.seniorcareapp.data.model.FtProfileItem
import com.bussiness.seniorcareapp.data.model.PosterItem
import com.bussiness.seniorcareapp.databinding.FragmentHomeBinding
import com.bussiness.seniorcareapp.ui.activity.AuthActivity
import com.bussiness.seniorcareapp.ui.activity.MainActivity
import com.bussiness.seniorcareapp.ui.adapter.BannerAdapter
import com.bussiness.seniorcareapp.ui.adapter.ExFacilitiesAdapter
import com.bussiness.seniorcareapp.ui.adapter.FtProviderAdapter
import com.bussiness.seniorcareapp.utils.SessionManager
import com.google.android.material.tabs.TabLayoutMediator
import androidx.core.graphics.toColorInt

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var exploreFacilityAdapter: ExFacilitiesAdapter
    private lateinit var featuredFacilityAdapter: FtProviderAdapter
    private lateinit var sessionManager: SessionManager


    private val bannerList = listOf(
        BannerData(R.drawable.banner_bg, "Explore trusted senior living facilities tailored to your needs. Search, compare, and connect effortlessly today!"),
        BannerData(R.drawable.banner_bg, "Compare top-rated care homes and find the right fit for your loved ones."),
        BannerData(R.drawable.banner_bg, "Join thousands who trust us for senior living solutions.")
    )

    private val exploreFacilityList = List(5) {
        PosterItem(R.drawable.poster, "Assisted Living", "Lorem ipsum dolor sit amet, consectetur adipiscing elit")
    }

    private val featuredFacilityList = List(2) {
        FtProfileItem(
            R.drawable.image_ic_fet,
            "Mathew John",
            "Experienced caregiver with passion for memory care.",
            "City, State, Country",
            "Assisted Living, Memory Care",
            "www.abc.com"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())

        if (!sessionManager.isLoggedIn()) {
            showLoginPrompt()
        } else {
            showSubscriptionPrompt()
        }

        setupBanner()
        setupRecyclerViews()
        clickListeners()

    }

    private fun setupBanner() {
        val viewPager = binding.bannerViewPager
        val tabIndicator = binding.tabIndicator

        viewPager.adapter = BannerAdapter(bannerList)

        TabLayoutMediator(tabIndicator, viewPager) { tab, _ ->
            tab.setCustomView(R.layout.banner_tab)
        }.attach()

        updateTabDots(0) // Set initial selected dot

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateTabDots(position)
            }
        })
    }

    private fun setupRecyclerViews() {
        // Horizontal Explore Facilities
        exploreFacilityAdapter = ExFacilitiesAdapter(exploreFacilityList) {
            findNavController().navigate(R.id.facilityListingFragment)
        }
        binding.exFacilitiesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = exploreFacilityAdapter
        }

        // Vertical Featured Facilities
        featuredFacilityAdapter = FtProviderAdapter(
            featuredFacilityList,
            onViewProfileClick = {
                findNavController().navigate(R.id.facilityDetailFragment)
            },
            onCallClick = {
                Toast.makeText(requireContext(), "Call Icon Clicked", Toast.LENGTH_SHORT).show()
            }
        )
        binding.fpRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = featuredFacilityAdapter
        }
    }

    private fun clickListeners() {
        var isBookmarked1 = false
        var isBookmarked2 = false
        binding.apply {
            ivMenu.setOnClickListener  { (activity as? MainActivity)?.openDrawer() }
            arrowIc.setOnClickListener { findNavController().navigate(R.id.facilityDetailFragment) }
            arrowIc1.setOnClickListener{ findNavController().navigate(R.id.facilityDetailFragment) }
            seeAllFacilities.setOnClickListener { findNavController() .navigate(R.id.facilityListingFragment)}
            bookmarkIcon.setOnClickListener {
                isBookmarked1 = !isBookmarked1
                val color = if (isBookmarked1) "#EA5B60" else "#FFFFFF" // or original color
                bookmarkIcon.setColorFilter(color.toColorInt())
            }
            bookmarkIcon2.setOnClickListener{
                isBookmarked2 = !isBookmarked2
                val color = if (isBookmarked2) "#EA5B60" else "#FFFFFF" // or original color
                bookmarkIcon2.setColorFilter(color.toColorInt())
            }

        }
    }

    private fun updateTabDots(selectedPosition: Int) {
        val tabLayout = binding.tabIndicator
        for (i in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(i)
            val imageView = tab?.customView?.findViewById<ImageView>(R.id.bannerTabDot)
            if (i == selectedPosition) {
                imageView?.setImageResource(R.drawable.banner_tab) // selected
            } else {
                imageView?.setImageResource(R.drawable.non_selected_banner_tab) // non-selected
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showLoginPrompt() {
        binding.fpRecyclerView.visibility = View.GONE
        binding.blurImageLogin.visibility = View.VISIBLE
        binding.lockedTxt.text = "Please Login to access the Provider"
        binding.btnLoginNow.text = "Login Now"
        binding.btnLoginNow.setOnClickListener {
            startActivity(Intent(requireContext(), AuthActivity::class.java))
        }
        binding.seeAllFacilities.visibility = View.GONE
        binding.credits.visibility = View.VISIBLE
        binding.ratingCard.visibility = View.VISIBLE
        binding.ratingCard1.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun showSubscriptionPrompt() {
        binding.fpRecyclerView.visibility = View.GONE
        binding.blurImageLogin.visibility = View.VISIBLE
        binding.lockedTxt.text = "You do not have enough credits.\nPlease purchase a subscription plan\nto access more provider information."
        binding.btnLoginNow.text = "Subscription plan"
        binding.btnLoginNow.setOnClickListener {
            binding.blurImageLogin.visibility = View.GONE
            binding.fpRecyclerView.visibility = View.VISIBLE
        }
        binding.seeAllFacilities.visibility = View.VISIBLE
        binding.credits.visibility = View.GONE
        binding.ratingCard.visibility = View.GONE
        binding.ratingCard1.visibility = View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
