package com.bussiness.seniorcareapp.ui.fragment.authentication

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.databinding.FragmentCreatePasswordBinding

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
