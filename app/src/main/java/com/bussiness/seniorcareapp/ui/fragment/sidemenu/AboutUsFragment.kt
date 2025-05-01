package com.bussiness.seniorcareapp.ui.fragment.sidemenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.databinding.FragmentAboutUsBinding
import androidx.core.view.isVisible
import com.bussiness.seniorcareapp.utils.SessionManager

class AboutUsFragment : Fragment() {

    private var _binding: FragmentAboutUsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        sessionManager =  SessionManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (sessionManager.isSkippedLogin()){
            binding.llDeleteAccountBtn.visibility = View.GONE
        }else{
            binding.llDeleteAccountBtn.visibility = View.VISIBLE
        }

        setUpClickListeners()
        setupExpandableAboutUsTextView()
    }

    private fun setUpClickListeners() {
        var isPrivacyExpanded = false
        var isTermsExpanded = false

        binding.apply {
            backIcon.setOnClickListener {
                findNavController().navigateUp()
            }

            llDeleteAccountBtn.setOnClickListener {
                findNavController().navigate(R.id.deleteAccountFragment)
            }

            txtPrivacyPolicy.setOnClickListener {
                if (privacyPolicyContent.isVisible && isPrivacyExpanded) {
                    privacyPolicyContent.visibility = View.GONE
                    isPrivacyExpanded = false
                } else {
                    privacyPolicyContent.visibility = View.VISIBLE
                    termsConditionContent.visibility = View.GONE
                    isPrivacyExpanded = true
                    isTermsExpanded = false
                }
            }

            txtTermsCondition.setOnClickListener {
                if (termsConditionContent.isVisible && isTermsExpanded) {
                    termsConditionContent.visibility = View.GONE
                    isTermsExpanded = false
                } else {
                    termsConditionContent.visibility = View.VISIBLE
                    privacyPolicyContent.visibility = View.GONE
                    isTermsExpanded = true
                    isPrivacyExpanded = false
                }
            }
        }
    }


    private fun setupExpandableAboutUsTextView() {
        val collapsedLines = 4
        var isExpanded = false

        binding.aboutUsContent.maxLines = collapsedLines
        binding.readMoreTxt.text = getString(R.string.read_more)

        binding.readMoreTxt.setOnClickListener {
            isExpanded = !isExpanded
            if (isExpanded) {
                binding.aboutUsContent.maxLines = Int.MAX_VALUE
                binding.readMoreTxt.text = getString(R.string.read_less)
            } else {
                binding.aboutUsContent.maxLines = collapsedLines
                binding.readMoreTxt.text = getString(R.string.read_more)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
