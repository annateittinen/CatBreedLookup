package com.ateittinen.example.catbreedlookup.data

import com.ateittinen.example.catbreedlookup.domain.BreedDataList
import retrofit2.Response

/**
 * Per https://docs.thecatapi.com/api-reference/models/breed
 * there are about 60 breeds (paginationCount).
 * Per https://docs.thecatapi.com/api-reference/breeds/breeds-list
 * current test run shows 67
 */

/**
 * Author: Anna Teittinen
 * Date: August 22, 2020
 */
class BreedDataResponse(response : Response<BreedDataList>) {
    val code : Int = response.code() // 200..300 -> OK
    val isSuccessful : Boolean = response.isSuccessful
    val message: String = response.message() //eg. "Internal Server Error"
    val errorBody = response.errorBody().toString() //eg. "Oops, an error occurred. Please contact support@rapi…]"
    val paginationCount: Int? = response.headers().get("Pagination-Count")?.toInt()
    val paginationLimit: Int? = response.headers().get("Pagination-Limit")?.toInt()
    val paginationPage: Int? = response.headers().get("Pagination-Page")?.toInt()
    val breedDataList : BreedDataList? = response.body()
}