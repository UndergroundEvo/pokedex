package com.sibers.pokemon_list.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.chip.Chip
import com.sibers.domain.entity.Pokemon
import com.sibers.pokemon_list.databinding.PokemonListItemBinding
import com.sibers.ui.R

private const val HIGHLIGHT_PAYLOAD = "highlight_payload"

class PokemonAdapter(
    private val onItemClick: (Int) -> Unit
) : PagingDataAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(PokemonDiffCallback) {

    object PokemonDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
            oldItem == newItem


        override fun getChangePayload(oldItem: Pokemon, newItem: Pokemon): Any? {
            return if (oldItem.isHighlighted != newItem.isHighlighted &&
                oldItem.copy(isHighlighted = newItem.isHighlighted) == newItem
            ) {
                HIGHLIGHT_PAYLOAD
            } else {
                null
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PokemonListItemBinding.inflate(inflater, parent, false)
        return PokemonViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) =
        onBindViewHolder(holder, position, mutableListOf())

    override fun onBindViewHolder(
        holder: PokemonViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val pokemon = getItem(position)
        if (pokemon == null) {
            return
        }

        if (payloads.isEmpty() || !payloads.contains(HIGHLIGHT_PAYLOAD)) {
            holder.bind(pokemon)
        } else {
            holder.updateHighlight(pokemon.isHighlighted)
        }
    }

    inner class PokemonViewHolder(
        private val binding: PokemonListItemBinding,
        private val onItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentPokemonId: Int? = null

        init {
            binding.root.setOnClickListener {
                currentPokemonId?.let(onItemClick)
            }
        }

        fun bind(pokemon: Pokemon) {
            currentPokemonId = pokemon.id
            binding.pokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            binding.pokemonImage.load(pokemon.image) {
                crossfade(true)
                placeholder(R.drawable.pokeball)
            }

            binding.pokemonType.removeAllViews()
            pokemon.type.forEach { type ->
                val chip = Chip(binding.root.context).apply {
                    text = type.replaceFirstChar { it.uppercase() }
                    isClickable = false
                    isCheckable = false
                    chipMinHeight = 65f
                }
                binding.pokemonType.addView(chip)
            }

            val statsText = pokemon.stats
                .filterKeys { it in listOf("hp", "attack", "defense") }
                .entries
                .joinToString(" ") { (statName, statValue) ->
                    "${statName.replaceFirstChar { it.uppercase() }}: $statValue"
                }
            binding.pokemonStats.text = statsText
            updateHighlight(pokemon.isHighlighted)
        }

        fun updateHighlight(isHighlighted: Boolean) {
            if (isHighlighted) {
                binding.root.setBackgroundResource(R.color.highlight_background_color)
            } else {
                binding.root.setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }
}