package com.sibers.pokemon_list.presentation.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sibers.pokemon_list.databinding.BottomSheetFilterBinding
import kotlinx.serialization.json.Json

const val FILTER_REQUEST_KEY = "filter_request"
const val FILTER_BUNDLE_KEY = "filter_criteria_json"

class FilterBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetFilterBinding? = null
    private val binding get() = _binding!!

    private var initialCriteriaJson: String? = null
    private var currentCriteria: FilterCriteria? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            initialCriteriaJson = it.getString(FILTER_BUNDLE_KEY)
            currentCriteria = initialCriteriaJson?.let { json ->
                try {
                    Json.decodeFromString<FilterCriteria>(json)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentCriteria?.let {
            binding.checkboxAttack.isChecked = it.attack
            binding.checkboxDefense.isChecked = it.defense
            binding.checkboxHp.isChecked = it.hp
        }

        binding.buttonApplyFilter.setOnClickListener {
            val resultCriteria = FilterCriteria(
                attack = binding.checkboxAttack.isChecked,
                defense = binding.checkboxDefense.isChecked,
                hp = binding.checkboxHp.isChecked
            )
            val resultJson = Json.encodeToString(resultCriteria)
            setFragmentResult(FILTER_REQUEST_KEY, bundleOf(FILTER_BUNDLE_KEY to resultJson))
            dismiss()
        }

        binding.buttonResetFilter.setOnClickListener {
            val resultCriteria = FilterCriteria(false, false, false)
            val resultJson = Json.encodeToString(resultCriteria)
            setFragmentResult(FILTER_REQUEST_KEY, bundleOf(FILTER_BUNDLE_KEY to resultJson))
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(currentCriteria: FilterCriteria): FilterBottomSheetDialogFragment {
            val criteriaJson = Json.encodeToString(currentCriteria)
            val args = Bundle().apply {
                putString(FILTER_BUNDLE_KEY, criteriaJson)
            }
            return FilterBottomSheetDialogFragment().apply {
                arguments = args
            }
        }

        const val TAG = "FilterBottomSheetDialogFragment"
    }
}