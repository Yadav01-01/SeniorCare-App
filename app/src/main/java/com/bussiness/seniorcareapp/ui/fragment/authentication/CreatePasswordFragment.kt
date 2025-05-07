package com.bussiness.seniorcareapp.ui.fragment.authentication

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.databinding.FragmentCreatePasswordBinding
import com.bussiness.seniorcareapp.utils.ErrorMessage

class CreatePasswordFragment : Fragment() {

    private var _binding: FragmentCreatePasswordBinding? = null
    private val binding get() = _binding!!
    private var isNewPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListener()
    }

    private fun clickListener() {
        binding.apply {
            newEyeIcon.setOnClickListener {
                isNewPasswordVisible = !isNewPasswordVisible
                togglePasswordVisibility(
                    isVisible = isNewPasswordVisible,
                    editText = edtNewPassword,
                    eyeIcon = newEyeIcon
                )
            }

            eyeIcon.setOnClickListener {
                isConfirmPasswordVisible = !isConfirmPasswordVisible
                togglePasswordVisibility(
                    isVisible = isConfirmPasswordVisible,
                    editText = edtCnfPassword,
                    eyeIcon = eyeIcon
                )
            }

            btnSubmit.setOnClickListener {
                if (dataValidation()){
                    findNavController().navigate(R.id.loginFragment)
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
            if (edtNewPassword.text.toString().trim().isEmpty()){
                edtNewPassword.error = ErrorMessage.PASSWORD_ERROR
                edtNewPassword.requestFocus()
                return false
            }
            if (edtCnfPassword.text.toString().trim().isEmpty()){
                edtCnfPassword.error = ErrorMessage.PASSWORD_ERROR
                edtCnfPassword.requestFocus()
                return false
            }
            if (edtNewPassword.text.toString().trim() != edtCnfPassword.text.toString().trim()){
                edtCnfPassword.error = ErrorMessage.PASSWORD_MISMATCH_ERROR
                edtCnfPassword.requestFocus()
                return false
            }
            if (!edtNewPassword.text.toString().trim().matches(Regex(ErrorMessage.PASSWORD_PATTERN))){
                edtNewPassword.error = ErrorMessage.PASSWORD_PATTERN_ERROR
                edtNewPassword.requestFocus()
                return false
            }
            if (!edtCnfPassword.text.toString().trim().matches(Regex(ErrorMessage.PASSWORD_PATTERN))){
                edtCnfPassword.error = ErrorMessage.PASSWORD_PATTERN_ERROR
                edtCnfPassword.requestFocus()
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
