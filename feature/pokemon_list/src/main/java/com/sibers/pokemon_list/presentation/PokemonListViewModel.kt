package com.sibers.pokemon_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.sibers.domain.SortCriteria
import com.sibers.domain.entity.Pokemon
import com.sibers.pokemon_list.domain.usecase.GetPokemonCountUseCase
import com.sibers.pokemon_list.domain.usecase.GetPokemonPagedOffsetUseCase
import com.sibers.pokemon_list.domain.usecase.GetPokemonPagedUseCase
import com.sibers.pokemon_list.presentation.filter.FilterCriteria
import com.sibers.pokemon_list.presentation.state.PokemonListIntent
import com.sibers.pokemon_list.presentation.state.PokemonListSideEffect
import com.sibers.pokemon_list.presentation.state.PokemonListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class PokemonListViewModel @Inject constructor(
    val getPagedUseCase: GetPokemonPagedUseCase,
    val getPagedUseCaseOffsetUseCase: GetPokemonPagedOffsetUseCase,
    val getCountUseCase: GetPokemonCountUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(PokemonListState())
    val state: StateFlow<PokemonListState> = _state.asStateFlow()

    private val _intents = MutableSharedFlow<PokemonListIntent>()
    private val intents: SharedFlow<PokemonListIntent> = _intents.asSharedFlow()

    private val _sideEffects = MutableSharedFlow<PokemonListSideEffect>()
    val sideEffects: SharedFlow<PokemonListSideEffect> = _sideEffects.asSharedFlow()

    private val _refreshCounter = MutableStateFlow(0)

    init {
        processIntents()
    }

    private val _reloadTrigger = combine(
        _state.map { it.currentSort },
        _state.map { it.randomInitialKey }
    ) { sort, initialKey -> Pair(sort, initialKey) }

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingDataFlow: Flow<PagingData<Pokemon>> =
        _reloadTrigger.flatMapLatest { (sort, initialKey) ->

            val pagingFlow = if (initialKey != null) {
                getPagedUseCaseOffsetUseCase.invoke(sort = sort, initialKey = initialKey)
            } else {
                getPagedUseCase.invoke(sort = sort)
            }

            pagingFlow
                .map { pagingData ->
                    pagingData.filter { pokemon ->
                        pokemon.id != _state.value.highlightedPokemonInHeader?.id
                    }
                }.onEach {
                    if (_state.value.randomInitialKey != null) {
                        _state.update { it.copy(randomInitialKey = null) }
                    }
                }
                .cachedIn(viewModelScope)
        }

    fun sendIntent(intent: PokemonListIntent) {
        viewModelScope.launch {
            _intents.emit(intent)
        }
    }

    private fun processIntents() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    PokemonListIntent.LoadData -> {
                        _refreshCounter.update { it + 1 }
                    }
                    PokemonListIntent.OpenFilterSheet -> emitSideEffect(PokemonListSideEffect.ShowFilterSheet)
                    is PokemonListIntent.ApplyFilter -> applyFilter(intent.criteria)
                    PokemonListIntent.OpenSortSheet -> emitSideEffect(
                        PokemonListSideEffect.ShowSortSheet(
                            _state.value.currentSort
                        )
                    )
                    PokemonListIntent.ReloadFromRandom -> {
                        val randomKey = Random.nextInt(0, 11)
                        _state.update { it.copy(randomInitialKey = randomKey) }
                        _state.update { it.copy(highlightedPokemonInHeader = null) }
                    }
                    is PokemonListIntent.ApplySort -> applySort(intent.criteria)
                    is PokemonListIntent.PokemonClicked -> handlePokemonClick(intent.pokemonId)
                    is PokemonListIntent.ProcessFilterOnList -> processFilterOnList(
                        intent.list,
                        intent.criteria
                    )
                }
            }
        }
    }

    private fun emitSideEffect(effect: PokemonListSideEffect) {
        viewModelScope.launch {
            _sideEffects.emit(effect)
        }
    }

    private fun applyFilter(criteria: FilterCriteria) {
        _state.update { it.copy(currentFilter = criteria) }

        if (criteria.isFilterActive) {
            emitSideEffect(PokemonListSideEffect.ScrollToAndHighlight(-1))
        } else {
            _state.update { it.copy(highlightedPokemonInHeader = null) }
            emitSideEffect(PokemonListSideEffect.ScrollToAndHighlight(-2))
        }
    }

    private fun applySort(criteria: SortCriteria) {
        _state.update {
            it.copy(
                currentSort = criteria,
                highlightedPokemonInHeader  = null,
                randomInitialKey = null
            )
        }
    }

    private fun handlePokemonClick(pokemonId: Long) {}

    private fun processFilterOnList(list: List<Pokemon>, criteria: FilterCriteria) {
        if (!criteria.isFilterActive || list.isEmpty()) {
            _state.update { it.copy(highlightedPokemonInHeader = null) }
            emitSideEffect(PokemonListSideEffect.ScrollToAndHighlight(-2))
            return
        }

        fun calculateStrength(pokemon: Pokemon): Int {
            var score = 0
            if (criteria.attack) score += pokemon.stats.get("attack") ?: 0
            if (criteria.defense) score += pokemon.stats.get("defense") ?: 0
            if (criteria.hp) score += pokemon.stats.get("hp") ?: 0
            return score
        }

        val finalStrongest = list.maxByOrNull { calculateStrength(it) }

        finalStrongest?.let { foundPokemon ->
            _state.update { it.copy(highlightedPokemonInHeader = foundPokemon) }
            emitSideEffect(PokemonListSideEffect.ScrollToAndHighlight(foundPokemon.id))
        } ?: run {
            _state.update { it.copy(highlightedPokemonInHeader = null) }
            emitSideEffect(PokemonListSideEffect.ScrollToAndHighlight(-2))
        }
    }
}