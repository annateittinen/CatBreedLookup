package com.ateittinen.example.catbreedlookup.domain

import com.ateittinen.example.catbreedlookup.data.BreedDataResponse
import com.ateittinen.example.catbreedlookup.data.ImageDataResponse
import com.ateittinen.example.catbreedlookup.data.LookupRepository
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

/**
 * Author: Anna Teittinen
 * Date: August 22, 2020
 */
class LookupUseCaseTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getBreeds() {
        val lookupRepository: LookupRepository = LookupRepository()
        val lookupUseCase: LookupUseCase = LookupUseCase(lookupRepository)
        val result: Single<BreedDataResponse> = lookupUseCase.getBreeds(1)
        val breedDataResponse: BreedDataResponse = result.blockingGet()
        println("returned! " + breedDataResponse.breedDataList?.size)
        println("paginationCount=" + breedDataResponse.paginationCount)
        println("paginationPage=" + breedDataResponse.paginationPage)
        println("paginationLimit=" + breedDataResponse.paginationLimit)
    }

    @Test
    fun getImage() {
        val lookupRepository: LookupRepository = LookupRepository()
        val lookupUseCase: LookupUseCase = LookupUseCase(lookupRepository)
        val result: Single<ImageDataResponse> = lookupUseCase.getImage("acur")
        val imageDataResponse: ImageDataResponse = result.blockingGet()
        println("returned! " + imageDataResponse.imageDataList?.size)
        println("paginationCount=" + imageDataResponse.paginationCount)
        println("paginationPage=" + imageDataResponse.paginationPage)
        println("paginationLimit=" + imageDataResponse.paginationLimit)

        println("selected breed id: " + imageDataResponse.imageDataList?.get(0)?.breeds?.get(0)?.id)
        println("selected breed name: " + imageDataResponse.imageDataList?.get(0)?.breeds?.get(0)?.name)
        println("selected breed alt names: " + imageDataResponse.imageDataList?.get(0)?.breeds?.get(0)?.alt_names)
        println("selected breed alt url: " + imageDataResponse.imageDataList?.get(0)?.url)
    }
}