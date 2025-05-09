package com.bussiness.seniorcareapp.ui.fragment

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.data.model.AmenitiesImage
import com.bussiness.seniorcareapp.data.model.SmallImageItem
import com.bussiness.seniorcareapp.databinding.FragmentDetailBinding
import com.bussiness.seniorcareapp.ui.activity.MainActivity
import com.bussiness.seniorcareapp.ui.adapter.AmenitiesImageAdapter
import com.bussiness.seniorcareapp.ui.adapter.DetailImageAdapter

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var amenitiesAdapter: AmenitiesImageAdapter? = null
    private var detailImageAdapter: DetailImageAdapter? = null

    private val detailImages = listOf(
        SmallImageItem(R.drawable.detail_map_ic),
        SmallImageItem(R.drawable.detail_map_ic),
        SmallImageItem(R.drawable.onb_img1),
        SmallImageItem(R.drawable.detail_map_ic),
        SmallImageItem(R.drawable.onb_img2),
        SmallImageItem(R.drawable.onb_img3)
    )

    private val amenitiesImages = listOf(
        AmenitiesImage(R.drawable.film1),
        AmenitiesImage(R.drawable.film2),
        AmenitiesImage(R.drawable.film1),
        AmenitiesImage(R.drawable.film2),
        AmenitiesImage(R.drawable.film1),
        AmenitiesImage(R.drawable.film2),
        AmenitiesImage(R.drawable.film1)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        setupExpandableAboutUsTextView()
        setupClickListeners()
    }

    private fun setupRecyclerViews() {
        detailImageAdapter = DetailImageAdapter(detailImages) { item ->
            binding.imageView.setImageResource(item.imageResId)
        }

        binding.imgRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = detailImageAdapter
            isNestedScrollingEnabled = false
        }

        binding.amenitiesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            amenitiesAdapter = AmenitiesImageAdapter(amenitiesImages)
            adapter = amenitiesAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun setupClickListeners() {
        var isBookmarked = false
        binding.apply {
            ivMenu.setOnClickListener {
                (activity as? MainActivity)?.openDrawer()
            }
            bookmarkIc.setOnClickListener {
                isBookmarked = !isBookmarked
                val iconRes = if (isBookmarked) R.drawable.vector_bookmark else R.drawable.selected_bookmark_ic
                bookmarkIc.setImageResource(iconRes)
            }
            compareNow.setOnClickListener {
                findNavController().navigate(R.id.compareFacilitiesFragment)
            }
        }
    }

    private fun setupExpandableAboutUsTextView() {
        val collapsedLines = 4
        var isExpanded = false

        binding.tvDescription.maxLines = collapsedLines
        binding.tvReadMore.text = Html.fromHtml("<u>Read more</u>", Html.FROM_HTML_MODE_LEGACY)

        binding.tvReadMore.setOnClickListener {
            isExpanded = !isExpanded
            binding.tvDescription.maxLines = if (isExpanded) Int.MAX_VALUE else collapsedLines
            val text = if (isExpanded) "<u>Read less</u>" else "<u>Read more</u>"
            binding.tvReadMore.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}