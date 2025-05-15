package com.bussiness.seniorcareapp.ui.fragment.authscreen

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.databinding.FragmentRegisterBinding
import com.bussiness.seniorcareapp.ui.activity.MainActivity
import com.bussiness.seniorcareapp.utils.ErrorMessage
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

        // ClickableSpan for "Terms & condition"
        val termsText = "Terms & condition"
        val termsStart = text.indexOf(termsText)
        val termsEnd = termsStart + termsText.length

        val termsClickable = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.putExtra("aboutUs","Terms & condition")
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = resources.getColor(R.color.purple)
            }
        }

        // ClickableSpan for "Privacy Policy"
        val privacyText = "Privacy Policy"
        val privacyStart = text.indexOf(privacyText)
        val privacyEnd = privacyStart + privacyText.length

        val privacyClickable = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.putExtra("aboutUs","Privacy Policy")
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = resources.getColor(R.color.purple)
            }
        }

        // Apply spans
        spannableString.setSpan(termsClickable, termsStart, termsEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(privacyClickable, privacyStart, privacyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Set text and enable link clicks
        binding.checkbox.text = spannableString
        binding.checkbox.movementMethod = LinkMovementMethod.getInstance()
        //        binding.checkbox.highlightColor = Color.TRANSPARENT // Optional: remove background color when clicked

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())
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
                if (dataValidation()){
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }
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

    private fun dataValidation() : Boolean {
        binding.apply {
            val passwordPattern = ErrorMessage.PASSWORD_PATTERN.toRegex()
            val isEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.text.toString().trim()).matches()
            val isPhone = android.util.Patterns.PHONE.matcher(edtEmail.text.toString().trim()).matches() && edtEmail.text.toString().trim().length >= 10

            if (edtEmail.text.toString().trim().isEmpty()){
                edtEmail.error = ErrorMessage.EMAIL_ERROR
                edtEmail.requestFocus()
                return false
            }
            if (edtYourPassword.text.toString().trim().isEmpty()){
                edtYourPassword.error = ErrorMessage.PASSWORD_ERROR
                edtYourPassword.requestFocus()
                return false
            }
            if (edtCnfPassword.text.toString().trim().isEmpty()){
                edtCnfPassword.error = ErrorMessage.PASSWORD_ERROR
                edtCnfPassword.requestFocus()
                return false
            }
            if (edtYourPassword.text.toString().trim() != edtCnfPassword.text.toString().trim()){
                edtCnfPassword.error = ErrorMessage.PASSWORD_MISMATCH_ERROR
                edtCnfPassword.requestFocus()
                return false
            }
            if (!checkbox.isChecked){
                checkbox.error = ErrorMessage.CHECKBOX_ERROR
                checkbox.requestFocus()
                return false
            }

            if (!edtYourPassword.text.toString().trim().matches(passwordPattern)) {
                edtYourPassword.error = ErrorMessage.PASSWORD_PATTERN_ERROR
                edtYourPassword.requestFocus()
                return false
            }
            if (!edtCnfPassword.text.toString().trim().matches(passwordPattern)) {
                edtCnfPassword.error = ErrorMessage.PASSWORD_PATTERN_ERROR
                edtCnfPassword.requestFocus()
                return false
            }
            if (!isEmail && !isPhone){
                edtEmail.error = ErrorMessage.EMAIL_ERROR
                edtEmail.requestFocus()
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
