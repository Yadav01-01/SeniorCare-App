package com.bussiness.seniorcareapp.ui.fragment.sidemenu

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bussiness.seniorcareapp.databinding.DialogDeleteAccountBinding
import com.bussiness.seniorcareapp.databinding.FragmentDeleteAccountBinding
import com.bussiness.seniorcareapp.ui.activity.AuthActivity
import com.bussiness.seniorcareapp.ui.adapter.ReasonAdapter

class DeleteAccountFragment : Fragment() {

    private var _binding: FragmentDeleteAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var deleteReasonAdapter: ReasonAdapter
    private val reasonList = listOf(
        "I don’t want to use Home Care anymore",
        "I’m using a different account",
        "I’m worried about my privacy ",
        "You are sending me too many emails/notifications ",
        "This app is not working properly ",
        "Other"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        clickListeners()
    }

    private fun setUpRecyclerView(){
        binding.deleteRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            deleteReasonAdapter = ReasonAdapter(
                reasonList,
                onOtherItemClick = {
                    binding.feedbackCard.visibility = View.VISIBLE
                    binding.deleteAccountBtn.visibility = View.VISIBLE
                },
                onItemClick = { selectedReason -> dialogDelete() }
            )
            adapter = deleteReasonAdapter
        }
    }

    private fun clickListeners() {
        binding.apply {
            backIcon.setOnClickListener { findNavController().navigateUp() }
            deleteAccountBtn.setOnClickListener { dialogDelete() }
        }
    }

    private fun dialogDelete() {
        val dialog = Dialog(requireContext())
        val binding = DialogDeleteAccountBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        binding.apply {
            cancelBtn.setOnClickListener { dialog.dismiss() }
            crossIcon.setOnClickListener { dialog.dismiss() }
            deleteBtn.setOnClickListener { val intent = Intent(requireContext(), AuthActivity::class.java)
                startActivity(intent)}
        }

        dialog.apply {
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)

            val displayMetrics = context.resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels
            val marginPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, displayMetrics).toInt()
            window?.setLayout(screenWidth - (2 * marginPx), ViewGroup.LayoutParams.WRAP_CONTENT)
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
