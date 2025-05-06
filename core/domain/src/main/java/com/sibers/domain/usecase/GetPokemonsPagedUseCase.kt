package com.sibers.domain.usecase

import androidx.paging.PagingData
import com.sibers.domain.entity.Pokemon
import kotlinx.coroutines.flow.Flow

interface GetPokemonsPagedUseCase {
    operator fun invoke() : Flow<PagingData<Pokemon>>
}