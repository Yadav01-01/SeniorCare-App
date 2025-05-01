package com.bussiness.seniorcareapp.ui.fragment.sidemenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.data.model.FAQItem
import com.bussiness.seniorcareapp.databinding.FragmentFaqBinding
import com.bussiness.seniorcareapp.ui.adapter.FAQAdapter


class FaqFragment : Fragment(R.layout.fragment_faq) {

    private var _binding: FragmentFaqBinding? = null
    private val binding get() = _binding!!
    private lateinit var faqAdapter: FAQAdapter
    private var faqList = listOf(FAQItem("How do I know if my loved one needs home care services?","You can start by scheduling a free consultation. We will assess your loved one's needs and recommend a customized care plan."),
        FAQItem("How do I know if my loved one needs home care services?","You can start by scheduling a free consultation. We will assess your loved one's needs and recommend a customized care plan."),
        FAQItem("How do I know if my loved one needs home care services?","You can start by scheduling a free consultation. We will assess your loved one's needs and recommend a customized care plan."),
        FAQItem("How do I know if my loved one needs home care services?","You can start by scheduling a free consultation. We will assess your loved one's needs and recommend a customized care plan."),
        FAQItem("How do I know if my loved one needs home care services?","You can start by scheduling a free consultation. We will assess your loved one's needs and recommend a customized care plan."),
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFaqBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        clickListeners()
    }

    private fun setUpRecyclerView(){
        binding.faqRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            faqAdapter = FAQAdapter(faqList)
            adapter = faqAdapter
        }
    }

    private fun clickListeners(){
        binding.apply {
            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
