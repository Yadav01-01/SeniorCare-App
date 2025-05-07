package com.bussiness.seniorcareapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.databinding.FragmentCompareFacilitiesBinding
import com.bussiness.seniorcareapp.ui.activity.MainActivity

class CompareFacilitiesFragment : Fragment() {

    private var _binding: FragmentCompareFacilitiesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompareFacilitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Receive selected facility ID
        parentFragmentManager.setFragmentResultListener("facility_selected", viewLifecycleOwner) { _, bundle ->
            val selectedFacilityId = bundle.getString("selected_facility_id")
            selectedFacilityId?.let {
                // TODO: Fetch and show facility details using ID (e.g., from local list or API)
                updateFacility1(it)
            }
        }

        clickListeners()
    }

    private fun clickListeners() {
        var isBookmarked = false
        binding.apply {
            val bundle = Bundle().apply {
                putBoolean("isForCompare", true)
            }

            changeFacilityBtn1.setOnClickListener {
                findNavController().navigate(R.id.facilityListingFragment, bundle)
            }

            changeFacilityBtn2.setOnClickListener {
                findNavController().navigate(R.id.facilityListingFragment, bundle)
            }

            ivMenu.setOnClickListener {
                (activity as? MainActivity)?.openDrawer()
            }

            bookmarkIcon.setOnClickListener {
                isBookmarked = !isBookmarked
                val color = if (isBookmarked) "#EA5B60" else "#FFFFFF" // or original color
                bookmarkIcon.setColorFilter(color.toColorInt())
            }

            arrowIc.setOnClickListener {
                findNavController().navigate(R.id.facilityDetailFragment)
            }
        }
    }

    // Dummy method to simulate updating facility 1 data in UI
    @SuppressLint("SetTextI18n")
    private fun updateFacility1(facilityId: String) {
        binding.apply {
            changeFacilityBtn1.text = "Change Facility"
            changeFacilityBtn2.text = "Change Facility"
            facilityImage1.setImageResource(R.drawable.genac_ic)
            facilityImage2.setImageResource(R.drawable.genac_ic)
            societyName1.text = "lorem ipsum"
            societyName2.text = "lorem ipsum"
            piInfo1.text = "John Deo"
            piInfo2.text = "Tom"
            floorInfo1.text = "10th of 21 Floors"
            floorInfo2.text = "2nd Floors"
            statusInfo1.text = "Immediately"
            statusInfo2.text= "Immediately"
            furnishedInfo1.text = "fully"
            furnishedInfo2.text = "half"
            builtYearInfo1.text = "2006"
            builtYearInfo2.text = "2010"
            garageInfo1.text = "1"
            garageInfo2.text = "2"
            overViewInfo1.text = "Lorem ipsum dolor sit amet consectetur. Sollicitudin aliquam donec morbi risus pellentesque.donec morbi risus pellentesque.donec morbi risus pellentesque. "
            overViewInfo2.text = "Lorem ipsum dolor sit amet consectetur. Sollicitudin aliquam donec morbi risus pellentesque.donec morbi risus pellentesque.donec morbi risus pellentesque. "
            amenitiesInfo1.text ="Lorem,  ipsum,  dolor, amet."
            amenitiesInfo2.text ="Lorem,  ipsum,  dolor, amet."
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
