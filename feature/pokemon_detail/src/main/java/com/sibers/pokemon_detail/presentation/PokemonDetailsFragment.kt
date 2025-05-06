package com.sibers.pokemon_detail.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.google.android.material.chip.Chip
import com.sibers.di.App
import com.sibers.domain.entity.Pokemon
import com.sibers.pokemon_detail.R
import com.sibers.pokemon_detail.databinding.PokemonDetailsLayoutBinding
import com.sibers.pokemon_detail.di.PokemonDetailsComponent
import com.sibers.pokemon_detail.presentation.state.PokemonDetailsIntent
import com.sibers.pokemon_detail.presentation.state.PokemonDetailsSideEffect
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonDetailsFragment : Fragment(R.layout.pokemon_details_layout) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: PokemonDetailsLayoutBinding? = null
    private val viewModel: PokemonDetailsViewModel by viewModels { viewModelFactory }

    private val binding get() = _binding!!

    companion object {
        private const val ARG_ID = "id"
        private const val TAG = "PokemonDetailsFragment"
        fun newInstance(id: String) = PokemonDetailsFragment().apply {
            arguments = Bundle().apply { putString(ARG_ID, id) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dependenciesProvider =
            (requireActivity().applicationContext as App).getDependenciesProvider()
        PokemonDetailsComponent.create(dependenciesProvider).inject(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = PokemonDetailsLayoutBinding.bind(view)
        val idString = arguments?.getString(ARG_ID)
        val pokemonId = idString?.toIntOrNull()
        if (pokemonId != null) {
            viewModel.sendIntent(PokemonDetailsIntent.LoadDetails(pokemonId))
        } else {
            Toast.makeText(requireContext(), "Error: Invalid Pokemon ID.", Toast.LENGTH_SHORT).show()
        }
        collectState()
        collectSideEffects()
    }

    private fun collectState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.progressBar.visibility =
                        if (state.isLoading) View.VISIBLE else View.GONE
                    binding.errorMessage.text = state.error?.message

                    if (state.isContentLoaded) {
                        displayPokemonDetails(state.pokemon!!)
                        binding.pokemonImage.visibility = View.VISIBLE
                        binding.pokemonName.visibility = View.VISIBLE
                        binding.pokemonId.visibility = View.VISIBLE
                        binding.pokemonStats.visibility = View.VISIBLE

                    } else {
                        binding.pokemonImage.visibility = View.GONE
                        binding.pokemonName.visibility = View.GONE
                        binding.pokemonId.visibility = View.GONE
                        binding.pokemonStats.visibility = View.GONE

                    }
                }
            }
        }
    }

    private fun collectSideEffects() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffects.collect { effect ->
                    when (effect) {
                        is PokemonDetailsSideEffect.ShowError -> {
                            Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                        PokemonDetailsSideEffect.NavigateBack -> {
                        }
                    }
                }
            }
        }
    }

    private fun displayPokemonDetails(pokemon: Pokemon) {
        binding.apply {
            pokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            pokemonId.text = buildString {
                append("#")
                append(pokemon.id)
            }

            binding.pokemonTypes.removeAllViews()
            pokemon.type.forEach { type ->
                val chip = Chip(binding.root.context).apply {
                    text = type.replaceFirstChar { it.uppercase() }
                    isClickable = false
                    isCheckable = false
                }
                binding.pokemonTypes.addView(chip)
            }
            pokemonImage.load(pokemon.image) {
                crossfade(true)
                placeholder(com.sibers.ui.R.drawable.pokeball)
            }

            val statsText = pokemon.stats
                .entries
                .joinToString("\n") { (statName, statValue) ->
                    "${statName.replaceFirstChar { it.uppercase() }}: $statValue"
                }

            pokemonStats.text = buildString {
                append("Height: ")
                append(pokemon.height)
                append("\n")
                append("Weight: ")
                append(pokemon.weight)
                append("\n")
                append(statsText)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}