package com.ateittinen.example.catbreedlookup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ateittinen.example.catbreedlookup.domain.LookupUseCase

/**
 * Author: Anna Teittinen
 * Date: August 22, 2020
 */
class LookupViewModelFactory(
    private val initialState: State?,
    private val lookupUseCase: LookupUseCase
) :ViewModelProvider.NewInstanceFactory() {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LookupViewModel(initialState, lookupUseCase) as T
    }
}