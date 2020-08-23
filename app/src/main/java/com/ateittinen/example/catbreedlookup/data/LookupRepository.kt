package com.ateittinen.example.catbreedlookup.data

import com.ateittinen.example.catbreedlookup.domain.BreedDataList
import com.ateittinen.example.catbreedlookup.domain.ImageDataList
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Author: Anna Teittinen
 * Date: August 22, 2020
 */
const val BASE_URL ="https://api.thecatapi.com"
const val HEADERS_KEY= "x-api-key: f99e3a27-20f4-4dff-8b91-28bf26959104"
//const val HEADERS_KEY= "x-api-key: SIGN-UP-FOR-KEY"

interface CatBreedInterface {
    @Headers(
        HEADERS_KEY
    )
    @GET("v1/breeds")
    open fun getAllBreedData(
        @Query("attach_breed") attach_breed: Int = 0,
        @Query("page") page: Int = 30,
        @Query("limit") limit: Int = 10  //number of objects to return on that page
    ): Call<BreedDataList>

    @Headers(
        HEADERS_KEY
    )
    @GET("v1/images/search")
    open fun getImageData(
        @Query("breed_id") breed_id: String = "",
        @Query("size") size: String = "small",
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 1  //number of objects to return on that page
    ): Call<ImageDataList>
}

class LookupRepository {

    private var catBreedInterface: CatBreedInterface

    init {
        val kotlinJsonAdapterFactory = KotlinJsonAdapterFactory()
        val moshi = Moshi.Builder().add(kotlinJsonAdapterFactory).build()
        val moshiConverterFactory = MoshiConverterFactory.create(moshi)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(moshiConverterFactory)
            .build()

        catBreedInterface = retrofit.create(CatBreedInterface::class.java)
    }

    fun getAllBreedData(nextPage: Int = 1): BreedDataResponse {
        val call = catBreedInterface.getAllBreedData(page = nextPage)
        val response : Response<BreedDataList> = call.execute()
        return BreedDataResponse(response)
    }

    fun getImageData(breedId: String): ImageDataResponse {
        val call = catBreedInterface.getImageData(breed_id = breedId)
        val response: Response<ImageDataList> = call.execute()
        return ImageDataResponse(response)
    }
}

