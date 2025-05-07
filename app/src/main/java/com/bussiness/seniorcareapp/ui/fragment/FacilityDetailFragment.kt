package com.bussiness.seniorcareapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.data.model.AmenitiesImage
import com.bussiness.seniorcareapp.data.model.SmallImageItem
import com.bussiness.seniorcareapp.databinding.FragmentFacilityDetailBinding
import com.bussiness.seniorcareapp.ui.activity.MainActivity
import com.bussiness.seniorcareapp.ui.adapter.AmenitiesImageAdapter
import com.bussiness.seniorcareapp.ui.adapter.DetailImageAdapter

class FacilityDetailFragment : Fragment() {

    private var _binding: FragmentFacilityDetailBinding? = null
    private val binding get() = _binding!!

    private val detailImages = listOf(
        SmallImageItem(R.drawable.img_ic_1),
        SmallImageItem(R.drawable.img_ic_1),
        SmallImageItem(R.drawable.img_ic_1),
        SmallImageItem(R.drawable.img_ic_1),
        SmallImageItem(R.drawable.img_ic_1),
        SmallImageItem(R.drawable.img_ic_1)
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
        _binding = FragmentFacilityDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        setupExpandableAboutUsTextView()
        setupClickListeners()
    }

    private fun setupRecyclerViews() {
        binding.imgRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = DetailImageAdapter(detailImages)
            isNestedScrollingEnabled = false
        }
        binding.amenitiesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = AmenitiesImageAdapter(amenitiesImages)
            isNestedScrollingEnabled = false
        }
    }

    private fun setupClickListeners() {
        var isBookmarked = false
        binding.apply {
            ivMenu.setOnClickListener {
                (activity as? MainActivity)?.openDrawer()
            }

            bookmarkIc?.setOnClickListener {
                isBookmarked = !isBookmarked
                val colorRes = if (isBookmarked) R.color.red else R.color.white
                val color = ContextCompat.getColor(requireContext(), colorRes)
                bookmarkIc.setColorFilter(color)
            }
        }
    }

    private fun setupExpandableAboutUsTextView() {
        val collapsedLines = 4
        var isExpanded = false

        binding.tvDescription.maxLines = collapsedLines
        binding.tvReadMore.text = getString(R.string.read_more)

        binding.tvReadMore.setOnClickListener {
            isExpanded = !isExpanded
            binding.tvDescription.maxLines = if (isExpanded) Int.MAX_VALUE else collapsedLines
            binding.tvReadMore.text = getString(if (isExpanded) R.string.read_less else R.string.read_more)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
