package com.bussiness.seniorcareapp.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.data.model.Amenity
import com.bussiness.seniorcareapp.data.model.Facility
import com.bussiness.seniorcareapp.databinding.DialogFilterBinding
import com.bussiness.seniorcareapp.databinding.FragmentFacilityListingBinding
import com.bussiness.seniorcareapp.databinding.ItemDropdownBinding
import com.bussiness.seniorcareapp.ui.activity.MainActivity
import com.bussiness.seniorcareapp.ui.adapter.AmenitiesAdapter
import com.bussiness.seniorcareapp.ui.adapter.FacilityListingAdapter
import androidx.core.graphics.drawable.toDrawable

class FacilityListingFragment : Fragment() {

    private var _binding: FragmentFacilityListingBinding? = null
    private val binding get() = _binding!!

    private lateinit var facilityListingAdapter: FacilityListingAdapter

    // Sample facility data
    private val facilityList = List(6) {
        Facility(
            R.drawable.banner_bg,
            "Facility ${it + 1}",
            "City, State, Country",
            "Assisted Living, Memory Care",
            "\$${(20 + it)}.9/-",
            id = "${1000 + it}" //  a unique ID for passing back
        )
    }

    private var isForCompare: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFacilityListingBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Read isForCompare flag
        isForCompare = arguments?.getBoolean("isForCompare", false) == true

        setupRecyclerView()
        clickListener()
    }

    private fun setupRecyclerView() {
        binding.facilityRecyclerView.isNestedScrollingEnabled = false
        binding.facilityRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            facilityListingAdapter = FacilityListingAdapter(facilityList) { facility ->

                if (isForCompare) {
                    // Return selected facility ID to CompareFacilitiesFragment
                    val result = Bundle().apply {
                        putString("selected_facility_id", facility.id)
                    }
                    setFragmentResult("facility_selected", result)
                    findNavController().popBackStack() // go back to compare screen
                } else {
                    // Normal flow - open details
                    val bundle = Bundle().apply {
                        putString("facility_id", facility.id)
                    }
                    findNavController().navigate(R.id.facilityDetailFragment, bundle)
                }
            }
            adapter = facilityListingAdapter
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun clickListener() {
        binding.apply {
            ivMenu.setOnClickListener { (activity as? MainActivity)?.openDrawer() }
            filterBtn.setOnClickListener { filterDialog() }
            joinNowBtn.setOnClickListener { findNavController().navigate(R.id.subscriptionFragment) }
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterDialog() {
        val dialog = Dialog(requireContext())
        val filterBinding = DialogFilterBinding.inflate(layoutInflater)
        dialog.setContentView(filterBinding.root)

        val amenityList = List(21) { Amenity("Amenity ${it + 1}") }

        val amenitiesAdapter = AmenitiesAdapter(amenityList) { amenity, isChecked ->
            amenity.isSelected = isChecked
        }

        filterBinding.filterAmenitiesRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = amenitiesAdapter
        }

        filterBinding.apply {
            crossIcon.setOnClickListener { dialog.dismiss() }
            submitBtn.setOnClickListener { dialog.dismiss() }

            typeText.setOnClickListener {
                showCustomDropdown(it, listOf("Apartment", "House", "Smart Home")) { selected ->
                    typeText.text = selected
                }
            }

            facility.setOnClickListener {
                showCustomDropdown(it, listOf("Assisted Living", "Independent Living","Memory Care")) { selected ->
                    facility.text = selected
                }
            }

            cityText.setOnClickListener {
                showCustomDropdown(it, listOf("City 1", "City 2", "City 3")) { selected ->
                    cityText.text = "City: $selected"
                }
            }

            roomsText.setOnClickListener {
                showCustomDropdown(it, listOf("1", "2", "3")) { selected ->
                    roomsText.text = "Rooms: $selected"
                }
            }

            statusText.setOnClickListener {
                showCustomDropdown(it, listOf("Healthcare", "Rehabilitation", "Pet-friendly")) { selected ->
                    statusText.text = selected
                }
            }
        }

        dialog.apply {
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            val displayMetrics = context.resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels
            val marginPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 15f, displayMetrics
            ).toInt()
            window?.setLayout(screenWidth - (2 * marginPx), ViewGroup.LayoutParams.WRAP_CONTENT)
            show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showCustomDropdown(anchor: View, items: List<String>, onItemClick: (String) -> Unit) {
        val inflater = LayoutInflater.from(requireContext())
        val binding = ItemDropdownBinding.inflate(inflater)

        binding.dropdownContainer.removeAllViews()

        val popupWindow = PopupWindow(
            binding.root,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        ).apply {
            elevation = 10f
            setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        }

        items.forEachIndexed { index, item ->
            val textView = TextView(requireContext()).apply {
                text = item
                setPadding(24, 20, 24, 20)
                setTextColor(Color.BLACK)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
                typeface = resources.getFont(R.font.poppins)
                setOnClickListener {
                    onItemClick(item)
                    popupWindow.dismiss()
                }
            }

            binding.dropdownContainer.addView(textView)

            // Add divider except after the last item
            if (index != items.lastIndex) {
                val divider = View(requireContext()).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 1
                    )
                    setBackgroundColor(Color.LTGRAY)
                }
                binding.dropdownContainer.addView(divider)
            }
        }

        popupWindow.showAsDropDown(anchor, 0, 10)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
