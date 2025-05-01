package com.bussiness.seniorcareapp.ui.fragment.authentication

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.databinding.FragmentRegisterBinding
import com.bussiness.seniorcareapp.ui.activity.MainActivity
import com.bussiness.seniorcareapp.utils.SessionManager

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false
    private var sessionManager: SessionManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        // Get the text from the string resource
        val text = getString(R.string.i_agree_to_the_terms_condition_and_privacy_policy)
        val spannableString = SpannableString(text)

        // Apply purple color and underline to "Terms & condition"
        val termsStart = text.indexOf("Terms & condition")
        val termsEnd = termsStart + "Terms & condition".length
        spannableString.setSpan(ForegroundColorSpan(resources.getColor(R.color.purple)), termsStart, termsEnd, 0)
        spannableString.setSpan(UnderlineSpan(), termsStart, termsEnd, 0)

        // Apply purple color and underline to "Privacy Policy"
        val privacyStart = text.indexOf("Privacy Policy")
        val privacyEnd = privacyStart + "Privacy Policy".length
        spannableString.setSpan(ForegroundColorSpan(resources.getColor(R.color.purple)), privacyStart, privacyEnd, 0)
        spannableString.setSpan(UnderlineSpan(), privacyStart, privacyEnd, 0)

        // Set the formatted text to the TextView using ViewBinding
        binding.checkbox.text = spannableString
        sessionManager = SessionManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickListener()
    }

    private fun clickListener() {
        binding.apply {
            loginTxt.setOnClickListener {
                findNavController().navigate(R.id.loginFragment)
            }

            eyeIcon.setOnClickListener {
                isPasswordVisible = !isPasswordVisible
                togglePasswordVisibility(
                    isVisible = isPasswordVisible,
                    editText = edtYourPassword,
                    eyeIcon = eyeIcon
                )
            }

            cnfEyeIcon.setOnClickListener {
                isConfirmPasswordVisible = !isConfirmPasswordVisible
                togglePasswordVisibility(
                    isVisible = isConfirmPasswordVisible,
                    editText = edtCnfPassword,
                    eyeIcon = cnfEyeIcon
                )
            }

            skipBtn.setOnClickListener {
                sessionManager?.setSkipLogin(true)
                sessionManager?.setLogin(false)
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }

            btnRegister.setOnClickListener {
                sessionManager?.setLogin(true)
                sessionManager?.setSkipLogin(false)
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    private fun togglePasswordVisibility(isVisible: Boolean, editText: EditText, eyeIcon: ImageView) {
        editText.inputType = if (isVisible) {
            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        eyeIcon.setImageResource(if (isVisible) R.drawable.eye_ic else R.drawable.close_eye)
        editText.setSelection(editText.text?.length ?: 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
