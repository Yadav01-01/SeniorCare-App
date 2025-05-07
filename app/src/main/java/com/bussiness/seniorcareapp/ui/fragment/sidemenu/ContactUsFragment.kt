package com.bussiness.seniorcareapp.ui.fragment.sidemenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bussiness.seniorcareapp.databinding.FragmentContactUsBinding
import com.bussiness.seniorcareapp.utils.ErrorMessage

class ContactUsFragment : Fragment() {

    private var _binding: FragmentContactUsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListeners()
    }

    private fun clickListeners() {
        binding.apply {
            backIcon.setOnClickListener { findNavController().navigateUp() }
            submitBtn.setOnClickListener {
                if (dataValidation()){
                    Toast.makeText(requireContext(), "Submitted Successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun dataValidation(): Boolean {
        binding.apply {

            if (edtName.text.toString().trim().isEmpty()) {
                edtName.error = ErrorMessage.NAME_ERROR
                edtName.requestFocus()
                return false
            }

            if ( edtEmail.text.toString().trim().isEmpty()) {
                edtEmail.error = ErrorMessage.EMAIL_ERROR
                edtEmail.requestFocus()
                return false
            }

            val emailPattern = ErrorMessage.EMAIL_PATTERN.toRegex()
            if (! edtEmail.text.toString().trim().matches(emailPattern)) {
                edtEmail.error = ErrorMessage.EMAIL_ERROR
                edtEmail.requestFocus()
                return false
            }

            if (edtPhone.text.toString().trim().isEmpty()){
                edtPhone.error = ErrorMessage.PHONE_ERROR
                edtPhone.requestFocus()
                return false
            }

            if(edtFeedback.text.toString().trim().isEmpty()){
                edtFeedback.error = ErrorMessage.FEEDBACK_ERROR
                edtFeedback.requestFocus()
                return false
            }
        }
        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
