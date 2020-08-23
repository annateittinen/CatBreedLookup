package com.ateittinen.example.catbreedlookup.presentation

import com.ateittinen.example.catbreedlookup.domain.BreedDataList
import com.ateittinen.example.catbreedlookup.domain.ImageDataList

/**
 * Author: Anna Teittinen
 * Date: August 22, 2020
 */
sealed class Change{
    object LoadingBreeds: Change()
    data class LoadedBreeds(val breedDataList: BreedDataList): Change()
    data class LoadBreedsError(val throwable: Throwable): Change()

    object LoadingImage: Change()
    data class LoadedImage(val imageDataList: ImageDataList): Change()
    data class LoadImageError(val throwable: Throwable): Change()
}