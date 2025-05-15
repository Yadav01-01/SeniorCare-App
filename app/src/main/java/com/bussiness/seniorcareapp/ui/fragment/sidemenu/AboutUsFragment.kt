package com.bussiness.seniorcareapp.ui.fragment.sidemenu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.databinding.FragmentAboutUsBinding
import com.bussiness.seniorcareapp.ui.activity.AuthActivity
import com.bussiness.seniorcareapp.utils.SessionManager

class AboutUsFragment : Fragment() {

    private var _binding: FragmentAboutUsBinding? = null
    private val binding get() = _binding!!

    private lateinit var sessionManager: SessionManager
    private var isPrivacyExpanded = false
    private var isTermsExpanded = false
    private var backStack: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        sessionManager = SessionManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve argument only once
        backStack = arguments?.getString("aboutUs")
        val sideMenuValue = arguments?.getString("sideMenu")

        // Hide delete account if skipped login
        val isSkipped = sessionManager.isSkippedLogin()
        val isPolicyPage = backStack == "Terms & condition" || backStack == "Privacy Policy"

        // Debug log
        Log.d("VisibilityCheck", "isSkipped: $isSkipped, backStack: $backStack")

        binding.llDeleteAccountBtn.visibility =
            if (isSkipped || isPolicyPage) View.GONE else View.VISIBLE


        // Handle system back press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            handleBackNavigation()
        }

        // Expand correct section based on argument
        isPrivacyExpanded = sideMenuValue == "Privacy Policy"
        isTermsExpanded = false
        binding.privacyPolicyContent.visibility = if (isPrivacyExpanded) View.VISIBLE else View.GONE
        binding.termsConditionContent.visibility = View.GONE

        // Set up listeners
        setUpClickListeners()
        setupExpandableAboutUsTextView()
    }

    private fun setUpClickListeners() {
        binding.apply {
            backIcon.setOnClickListener {
                handleBackNavigation()
            }

            llDeleteAccountBtn.setOnClickListener {
                findNavController().navigate(R.id.deleteAccountFragment)
            }

            txtPrivacyPolicy.setOnClickListener {
                isPrivacyExpanded = !isPrivacyExpanded
                isTermsExpanded = false
                privacyPolicyContent.visibility = if (isPrivacyExpanded) View.VISIBLE else View.GONE
                termsConditionContent.visibility = View.GONE
            }

            txtTermsCondition.setOnClickListener {
                isTermsExpanded = !isTermsExpanded
                isPrivacyExpanded = false
                termsConditionContent.visibility = if (isTermsExpanded) View.VISIBLE else View.GONE
                privacyPolicyContent.visibility = View.GONE
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
            binding.aboutUsContent.maxLines = if (isExpanded) Int.MAX_VALUE else collapsedLines
            binding.readMoreTxt.text = getString(if (isExpanded) R.string.read_less else R.string.read_more)
        }
    }

    private fun handleBackNavigation() {
        if (backStack == "Terms & condition" || backStack == "Privacy Policy") {
            val intent = Intent(requireContext(), AuthActivity::class.java)
            intent.putExtra("backNav", backStack)
            startActivity(intent)
            requireActivity().finish()
        } else {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
