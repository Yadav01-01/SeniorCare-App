package com.bussiness.seniorcareapp.ui.fragment.authentication

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.databinding.DialogLoginSucessBinding
import com.bussiness.seniorcareapp.databinding.FragmentLoginBinding
import com.bussiness.seniorcareapp.ui.activity.MainActivity
import com.bussiness.seniorcareapp.utils.SessionManager

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager
    private var isPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())
        clickListeners()
    }

    private fun clickListeners() {
        binding.apply {
            btnLogin.setOnClickListener {
                sessionManager.setLogin(true)
                sessionManager.setSkipLogin(false)
                showLoginSuccessDialog()
            }

            signUpTxt.setOnClickListener {
                findNavController().navigate(R.id.registerFragment)
            }

            txtForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.forgotPasswordFragment)
            }

            skipBtn.setOnClickListener {
                sessionManager.setSkipLogin(true)
                sessionManager.setLogin(false)
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }

            eyeIcon.setOnClickListener {
                isPasswordVisible = !isPasswordVisible
                edtPassword.inputType = if (isPasswordVisible) {
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                } else {
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }
                eyeIcon.setImageResource(
                    if (isPasswordVisible) R.drawable.eye_ic else R.drawable.close_eye
                )
                edtPassword.text?.let { edtPassword.setSelection(it.length) }
            }
        }
    }

    private fun showLoginSuccessDialog() {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogLoginSucessBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.okayBtn.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }

        dialog.apply {
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            val displayMetrics = context.resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels
            val marginPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 15f, displayMetrics
            ).toInt()
            window?.setLayout(screenWidth - (2 * marginPx), ViewGroup.LayoutParams.WRAP_CONTENT)
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
