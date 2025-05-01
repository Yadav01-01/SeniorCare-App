package com.bussiness.seniorcareapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.data.model.SmallImageItem
import com.bussiness.seniorcareapp.databinding.FragmentFacilityDetailBinding
import com.bussiness.seniorcareapp.ui.adapter.DetailImageAdapter

class FacilityDetailFragment : Fragment() {

    private var _binding: FragmentFacilityDetailBinding? = null
    private val binding get() = _binding!!
    private var detailImageAdapter = DetailImageAdapter(emptyList())
    private var imageList = listOf(SmallImageItem(R.drawable.img_ic_1), SmallImageItem(R.drawable.img_ic_1),SmallImageItem(R.drawable.img_ic_1), SmallImageItem(R.drawable.img_ic_1),
        SmallImageItem(R.drawable.img_ic_1), SmallImageItem(R.drawable.img_ic_1))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFacilityDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setupExpandableAboutUsTextView()
        clickListener()
    }

    private fun setUpRecyclerView() {
        binding.imgRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            detailImageAdapter = DetailImageAdapter(imageList)
            adapter = detailImageAdapter
        }
    }

    private fun clickListener(){
        binding.apply {
            ivMenu.setOnClickListener {  }
        }
    }

    private fun setupExpandableAboutUsTextView() {
        val collapsedLines = 4
        var isExpanded = false

        binding.tvDescription.maxLines = collapsedLines
        binding.tvReadMore.text = getString(R.string.read_more)

        binding.tvReadMore.setOnClickListener {
            isExpanded = !isExpanded
            if (isExpanded) {
                binding.tvDescription.maxLines = Int.MAX_VALUE
                binding.tvReadMore.text = getString(R.string.read_less)
            } else {
                binding.tvDescription.maxLines = collapsedLines
                binding.tvReadMore.text = getString(R.string.read_more)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
