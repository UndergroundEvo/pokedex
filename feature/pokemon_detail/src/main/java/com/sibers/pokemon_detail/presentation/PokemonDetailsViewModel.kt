package com.sibers.pokemon_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sibers.domain.entity.Pokemon
import com.sibers.pokemon_detail.domain.usecase.GetPokemonDetailsUseCase
import com.sibers.pokemon_detail.presentation.state.PokemonDetailsIntent
import com.sibers.pokemon_detail.presentation.state.PokemonDetailsSideEffect
import com.sibers.pokemon_detail.presentation.state.PokemonDetailsState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonDetailsViewModel @Inject constructor(
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(PokemonDetailsState())
    val state: StateFlow<PokemonDetailsState> = _state.asStateFlow()

    private val _intents = MutableSharedFlow<PokemonDetailsIntent>()
    private val intents: SharedFlow<PokemonDetailsIntent> = _intents.asSharedFlow()

    private val _sideEffects = MutableSharedFlow<PokemonDetailsSideEffect>()
    val sideEffects: SharedFlow<PokemonDetailsSideEffect> = _sideEffects.asSharedFlow()

    init {
        processIntents()
    }

    fun sendIntent(intent: PokemonDetailsIntent) {
        viewModelScope.launch {
            _intents.emit(intent)
        }
    }

    private fun processIntents() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    is PokemonDetailsIntent.LoadDetails -> loadPokemonDetails(intent.pokemonId)
                    is PokemonDetailsIntent.DetailsLoaded -> handleDetailsLoaded(intent.pokemon)
                    is PokemonDetailsIntent.ErrorLoading -> handleErrorLoading(intent.throwable)
                    PokemonDetailsIntent.BackPressed -> {}
                }
            }
        }
    }

    private fun loadPokemonDetails(pokemonId: Int) {
        if (_state.value.isLoading || _state.value.pokemonId == pokemonId) {
            return
        }

        _state.update {
            it.copy(
                isLoading = true,
                error = null,
                pokemon = null,
                pokemonId = pokemonId
            )
        }

        viewModelScope.launch {
            try {
                val pokemon = getPokemonDetailsUseCase(pokemonId)
                if (pokemon != null) {
                    sendIntent(PokemonDetailsIntent.DetailsLoaded(pokemon))
                } else {
                    sendIntent(PokemonDetailsIntent.ErrorLoading(Exception("Pokemon with ID $pokemonId not found.")))
                }
            } catch (e: Exception) {
                sendIntent(PokemonDetailsIntent.ErrorLoading(e))
            }
        }
    }

    private fun handleDetailsLoaded(pokemon: Pokemon) {
        _state.update {
            it.copy(
                isLoading = false,
                error = null,
                pokemon = pokemon
            )
        }
    }

    private fun handleErrorLoading(throwable: Throwable) {
        _state.update {
            it.copy(
                isLoading = false,
                error = throwable,
                pokemon = null
            )
        }
        emitSideEffect(PokemonDetailsSideEffect.ShowError(throwable.message ?: "An unknown error occurred"))
    }

    private fun emitSideEffect(effect: PokemonDetailsSideEffect) {
        viewModelScope.launch {
            _sideEffects.emit(effect)
        }
    }
}