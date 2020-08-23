package com.ateittinen.example.catbreedlookup.domain

import com.ateittinen.example.catbreedlookup.data.BreedDataResponse
import com.ateittinen.example.catbreedlookup.data.ImageDataResponse
import com.ateittinen.example.catbreedlookup.data.LookupRepository
import io.reactivex.Single

/**
 * Author: Anna Teittinen
 * Date: August 22, 2020
 */
class LookupUseCase(val lookupRepository: LookupRepository) {
    fun getBreeds(nextPage: Int = 1): Single<BreedDataResponse> {
        val breedDateResponse : BreedDataResponse = lookupRepository.getAllBreedData(nextPage)
        return Single.just(breedDateResponse)
    }

    fun getImage(breedId: String): Single<ImageDataResponse> {
        val imageDataResponse : ImageDataResponse = lookupRepository.getImageData(breedId)
        return Single.just(imageDataResponse)
    }
}