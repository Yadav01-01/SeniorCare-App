package com.bussiness.seniorcareapp.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
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
import androidx.core.text.HtmlCompat

class FacilityListingFragment : Fragment() {

    private var _binding: FragmentFacilityListingBinding? = null
    private val binding get() = _binding!!

    private lateinit var facilityListingAdapter: FacilityListingAdapter
    private var isForCompare: Boolean = false

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
        binding.facilityRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        facilityListingAdapter = FacilityListingAdapter(
            facilityList,
            onBookmarkClick = {
                Toast.makeText(requireContext(), "${it.name} bookmarked: ${it.isBookmarked}", Toast.LENGTH_SHORT).show()
            },
                    onItemClick = { facility ->
                if (isForCompare) {
                    // Return selected facility ID to CompareFacilitiesFragment
                    val result = Bundle().apply {
                        putString("selected_facility_id", facility.id)
                    }
                    setFragmentResult("facility_selected", result)
                    findNavController().popBackStack()
                } else {
                    // Normal flow - open details
                    val bundle = Bundle().apply {
                        putString("facility_id", facility.id)
                    }
                    findNavController().navigate(R.id.facilityDetailFragment, bundle)
                }
            }
        )

        binding.facilityRecyclerView.adapter = facilityListingAdapter
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

        filterBinding.priceSlider.addOnChangeListener { slider, _, _ ->
            val values = filterBinding.priceSlider.values
            if (values.size >= 2) {
                val minPrice = values[0].toInt()
                val maxPrice = values[1].toInt()

                val fullText = "Price range from $ $minPrice to $ $maxPrice"
                val spannable = SpannableStringBuilder(fullText)

                val minStart = fullText.indexOf("$ $minPrice")
                val minEnd = minStart + "$ $minPrice".length
                val maxStart = fullText.indexOf("$ $maxPrice")
                val maxEnd = maxStart + "$ $maxPrice".length

                spannable.setSpan(StyleSpan(Typeface.BOLD), minStart, minEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannable.setSpan(RelativeSizeSpan(1.2f), minStart, minEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                spannable.setSpan(StyleSpan(Typeface.BOLD), maxStart, maxEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannable.setSpan(RelativeSizeSpan(1.2f), maxStart, maxEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                filterBinding.priceRangeText.text = spannable

                // Use min and max
            } else {
                Log.e("FilterDialog", "Unexpected slider values: $values")
            }
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

        val popupWindow = PopupWindow(binding.root, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true).apply {
            elevation = 10f
            setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        }

        items.forEachIndexed { index, item ->
            val textView = TextView(requireContext()).apply {
                text = item
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
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
                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1)
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
