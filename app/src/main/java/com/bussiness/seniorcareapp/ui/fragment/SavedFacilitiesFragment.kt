package com.bussiness.seniorcareapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.data.model.Facility
import com.bussiness.seniorcareapp.databinding.FragmentSavedFacilitiesBinding
import com.bussiness.seniorcareapp.ui.activity.MainActivity
import com.bussiness.seniorcareapp.ui.adapter.FacilityAdapter

class SavedFacilitiesFragment : Fragment() {

    private var _binding: FragmentSavedFacilitiesBinding? = null
    private val binding get() = _binding!!
    private lateinit var savedFacilitiesAdapter: FacilityAdapter
    private var isBookmarked = false
    private val savedFacilitiesList = List(6){
        Facility(R.drawable.banner_bg,"Facility Name","City, State, Country","Assisted Living, Memory Care","\$25.9/-","")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedFacilitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        clickListeners()
    }

    private fun setupRecyclerView() {
        binding.savedFacilitiesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            savedFacilitiesAdapter = FacilityAdapter(savedFacilitiesList,
                onItemClick = {
                    findNavController().navigate(R.id.facilityDetailFragment)
                },
                onBookmarkClick = { facility ->
                    Toast.makeText(requireContext(), "${facility.name} bookmarked: ${facility.isBookmarked}", Toast.LENGTH_SHORT).show()
                }
            )
            adapter = savedFacilitiesAdapter
        }
    }



    private fun clickListeners() {
        binding.apply {
            ivMenu.setOnClickListener { (activity as? MainActivity)?.openDrawer() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
