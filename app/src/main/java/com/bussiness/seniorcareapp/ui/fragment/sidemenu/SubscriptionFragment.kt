package com.bussiness.seniorcareapp.ui.fragment.sidemenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bussiness.seniorcareapp.data.model.SubscriptionPlan
import com.bussiness.seniorcareapp.databinding.FragmentSubscriptionBinding
import com.bussiness.seniorcareapp.ui.adapter.SubscriptionAdapter

class SubscriptionFragment : Fragment() {

    private var _binding: FragmentSubscriptionBinding? = null
    private val binding get() = _binding!!
    private lateinit var subscriptionAdapter: SubscriptionAdapter
    private val plans = listOf(
        SubscriptionPlan("Basic Plan", "$7.00", listOf(
            "Limited Features", "Basic Support", "Trial for Premium Features", "Community Access", "No Commitment")),
        SubscriptionPlan("Exclusive Plan", "$24.99", listOf(
            "Premium Features", "Custom Integrations", "Personalized Onboarding", "10 Provider Call Support", "10 Facility View Access")),
        SubscriptionPlan("Platinum Plan", "$70.00", listOf(
            "Limited Features", "Basic Support", "Trial for Premium Features", "Community Access", "No Commitment")),

    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubscriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupClickListeners()
    }

    private fun setupRecyclerView(){
        binding.subscriptionRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            subscriptionAdapter = SubscriptionAdapter(plans)
            adapter = subscriptionAdapter
        }
    }

    private fun setupClickListeners(){
        binding.apply {
            ivMenu.setOnClickListener { findNavController().navigateUp() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
