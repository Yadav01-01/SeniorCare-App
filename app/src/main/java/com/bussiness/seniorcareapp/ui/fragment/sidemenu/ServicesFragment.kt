package com.bussiness.seniorcareapp.ui.fragment.sidemenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.data.model.ServiceItem
import com.bussiness.seniorcareapp.databinding.FragmentServicesBinding
import com.bussiness.seniorcareapp.ui.adapter.ServicesAdapter

class ServicesFragment : Fragment() {

    private var _binding: FragmentServicesBinding? = null
    private val binding get() = _binding!!
    private lateinit var serviceAdapter : ServicesAdapter
    private var serviceList = listOf(ServiceItem("Senior Living Communities","Discover a variety of senior living communities that offer a comfortable and engaging lifestyle.","Independent Living","Assisted Living","Memory Care","Continuing Care Retirement Communities","Find the perfect community that matches your lifestyle and needs.",
        R.drawable.service),
        ServiceItem("Assisted Living Facilities","Personalized care and support for seniors who need help with daily activities while maintaining their independence.","24/7 Care Assistance","Medication Management","Wellness Programs","Recreational Activities","Explore assisted living options that prioritize your well-being.",
            R.drawable.service),
        ServiceItem("Memory Care Services","Specialized care for seniors living with Alzheimer's, dementia, or memory-related conditions.","Secure Environment","Cognitive Therapy Programs"," Trained Caregivers"," Family Support Services","Find trusted memory care facilities designed for comfort and safety.",
            R.drawable.service))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpClickListener()
    }

    private fun setUpRecyclerView(){
        binding.serviceRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            serviceAdapter = ServicesAdapter(serviceList)
            adapter = serviceAdapter
        }
    }

    private fun setUpClickListener(){
        binding.apply {
            backBtn.setOnClickListener { findNavController().navigateUp() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
