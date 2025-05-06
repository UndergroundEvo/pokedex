package com.sibers.pokemon_list.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.github.terrakok.cicerone.Router
import com.google.android.material.chip.Chip
import com.sibers.di.App
import com.sibers.di.PokemonAppScreens
import com.sibers.domain.entity.Pokemon
import com.sibers.pokemon_list.R
import com.sibers.pokemon_list.databinding.PokemonListItemBinding
import com.sibers.pokemon_list.databinding.PokemonListLayoutBinding
import com.sibers.pokemon_list.di.PokemonListComponent
import com.sibers.pokemon_list.presentation.adapter.PokemonAdapter
import com.sibers.pokemon_list.presentation.filter.FILTER_BUNDLE_KEY
import com.sibers.pokemon_list.presentation.filter.FILTER_REQUEST_KEY
import com.sibers.pokemon_list.presentation.filter.FilterBottomSheetDialogFragment
import com.sibers.pokemon_list.presentation.filter.FilterCriteria
import com.sibers.pokemon_list.presentation.state.PokemonListIntent
import com.sibers.pokemon_list.presentation.state.PokemonListSideEffect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PokemonListFragment : Fragment(R.layout.pokemon_list_layout) {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var router: Router
    @Inject lateinit var pokemonScreens: PokemonAppScreens
    private lateinit var adapter: PokemonAdapter

    private val viewModel: PokemonListViewModel by viewModels { viewModelFactory }
    private lateinit var highlightedItemBinding: PokemonListItemBinding

    private var _binding: PokemonListLayoutBinding? = null
    private val binding get() = _binding!!


    companion object {
        fun newInstance() = PokemonListFragment()
        const val TAG = "PokemonListFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dependenciesProvider =
            (requireActivity().applicationContext as App).getDependenciesProvider()
        PokemonListComponent.create(dependenciesProvider).inject(this)

        adapter = PokemonAdapter { id ->
            router.navigateTo(pokemonScreens.pokemonDetails(id.toString()))
        }
    }

    private fun setupFragmentResultListeners() {
        childFragmentManager.setFragmentResultListener(
            FILTER_REQUEST_KEY,
            viewLifecycleOwner
        ) { requestKeyCallback, bundle ->
            if (requestKeyCallback == FILTER_REQUEST_KEY) {
                val filterCriteriaJson = bundle.getString(FILTER_BUNDLE_KEY)

                val filterCriteria = filterCriteriaJson?.let { json ->
                    try {
                        Json.decodeFromString<FilterCriteria>(json)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }
                }

                filterCriteria?.let {
                    viewModel.sendIntent(PokemonListIntent.ApplyFilter(it))
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = PokemonListLayoutBinding.bind(view)

        setupToolbar()
        setupRecyclerView()
        setupFragmentResultListeners()

        collectState()
        collectSideEffects()
        setupHighlightedItemClickListener()

        if (savedInstanceState == null) {
            viewModel.sendIntent(PokemonListIntent.LoadData)
        }
    }

    private fun setupHighlightedItemClickListener() {
        highlightedItemBinding = PokemonListItemBinding.bind(binding.highlightedPokemonItem.root)
        binding.highlightedPokemonItem.root.setOnClickListener {
            val highlightedPokemon = viewModel.state.value.highlightedPokemonInHeader
            if (highlightedPokemon != null) {
                router.navigateTo(pokemonScreens.pokemonDetails(highlightedPokemon.id.toString())) // Int в String для навигации
            } else {
            }
        }
    }

    private fun setupToolbar() {
        binding.pokemonListTopappbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.appbar_menu_filter -> {
                    viewModel.sendIntent(PokemonListIntent.OpenFilterSheet)
                    true
                }
                R.id.appbar_menu_reload -> {
                    viewModel.sendIntent(PokemonListIntent.ReloadFromRandom)
                    true
                }
                else -> false
            }
        }
    }


    private fun setupRecyclerView() {
        binding.pokemonRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.pokemonRecyclerview.adapter = adapter
    }

    private fun collectState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.pagingDataFlow.collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
                launch {
                    viewModel.state.collectLatest { state ->
                        if (state.highlightedPokemonInHeader != null) {
                            binding.highlightedPokemonItem.root.visibility = View.VISIBLE
                            bindHighlightedPokemon(state.highlightedPokemonInHeader)
                        } else {
                            binding.highlightedPokemonItem.root.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun bindHighlightedPokemon(pokemon: Pokemon) {
        highlightedItemBinding.apply {
            pokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            pokemonImage.load(pokemon.image) {
                crossfade(true)
                placeholder(com.sibers.ui.R.drawable.pokeball)
            }

            pokemonType.removeAllViews()
            pokemon.type.forEach { type ->
                val chip = Chip(root.context).apply {
                    text = type.replaceFirstChar { it.uppercase() }
                    isClickable = false
                    isCheckable = false
                    chipMinHeight = 65f
                }
                pokemonType.addView(chip)
            }

            val statsText = pokemon.stats
                .filterKeys { it in listOf("hp", "attack", "defense") }
                .entries
                .joinToString(" ") { (statName, statValue) ->
                    "${statName.replaceFirstChar { it.uppercase() }}: $statValue"
                }
            pokemonStats.text = statsText
            root.setBackgroundResource(com.sibers.ui.R.color.highlight_background_color)
        }
    }

    private fun collectSideEffects() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffects.collect { effect ->
                    when (effect) {
                        PokemonListSideEffect.ShowFilterSheet -> {
                            val currentFilter = viewModel.state.value.currentFilter
                            FilterBottomSheetDialogFragment.newInstance(currentFilter)
                                .show(childFragmentManager, FilterBottomSheetDialogFragment.TAG)
                        }

                        is PokemonListSideEffect.ShowSortSheet -> { }

                        is PokemonListSideEffect.ScrollToAndHighlight -> {
                            handleScrollToAndHighlight(effect.pokemonId) // pokemonId Int
                        }
                    }
                }
            }
        }
    }

    private fun handleScrollToAndHighlight(pokemonId: Int) {
        when (pokemonId) {
            -1 -> {
                val currentList = adapter.snapshot().items.filterNotNull()
                viewModel.sendIntent(
                    PokemonListIntent.ProcessFilterOnList(
                        currentList,
                        viewModel.state.value.currentFilter
                    )
                )
            }

            -2 -> {
                binding.pokemonRecyclerview.scrollToPosition(0)
            }

            else -> { binding.pokemonRecyclerview.scrollToPosition(0) }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}