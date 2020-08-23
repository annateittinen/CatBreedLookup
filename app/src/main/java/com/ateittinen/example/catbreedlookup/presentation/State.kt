package com.ateittinen.example.catbreedlookup.presentation

import com.ateittinen.example.catbreedlookup.domain.BreedDataList
import com.ateittinen.example.catbreedlookup.domain.ImageDataList
import com.ww.roxie.BaseState

/**
 * Author: Anna Teittinen
 * Date: August 22, 2020
 */
sealed class State : BaseState{
    object Idle: State()

    object LoadingBreeds: State()
    data class LoadedBreeds(val breedsDataList: BreedDataList): State()
    data class LoadingBreedsError(val throwable: Throwable): State()

    object LoadingImage: State()
    data class LoadedImage(val imageDataList: ImageDataList): State()
    data class LoadingImageError(val throwable: Throwable): State()
}