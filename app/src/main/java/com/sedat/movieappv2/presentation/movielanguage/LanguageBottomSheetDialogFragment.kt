package com.sedat.movieappv2.presentation.movielanguage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sedat.movieappv2.R
import com.sedat.movieappv2.databinding.FragmentLanguageBottomSheetDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LanguageBottomSheetDialogFragment(private val isSelect: (String) -> Unit) : BottomSheetDialogFragment() {

    private var _binding: FragmentLanguageBottomSheetDialogBinding ?= null
    private val binding get() = _binding!!

    private val viewModel: MovieLanguageViewModel by viewModels()
    private val adapterLanguage = MovieLanguageAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLanguageBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCharacterRecyclerView()
        collectFromViewModel()

        adapterLanguage.setOnItemClickListener {
            viewModel.selectLanguage(it)
            isSelect.invoke(it)
        }
    }

    private fun collectFromViewModel(){
        lifecycleScope.launch {
            launch {
                viewModel.languages.collect{ resource ->
                    resource.data?.let { languageList ->
                        adapterLanguage.submitList(languageList)
                    } ?: adapterLanguage.submitList(listOf())
                }
            }
        }
    }

    private fun setupCharacterRecyclerView() {

        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val itemWidth = resources.getDimensionPixelSize(R.dimen.language_item_width)
        val spanCount = screenWidth / itemWidth

        val layoutManager = GridLayoutManager(requireContext(), spanCount - 1)

        binding.apply {
            recyclerLanguages.apply {
                setLayoutManager(layoutManager)
                setHasFixedSize(true)
                adapter = adapterLanguage
            }
        }
    }
}