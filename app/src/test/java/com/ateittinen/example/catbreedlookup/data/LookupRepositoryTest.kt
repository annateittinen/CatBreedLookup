package com.ateittinen.example.catbreedlookup.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * Author: Anna Teittinen
 * Date: August 22, 2020
 */
class LookupRepositoryTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getBreeds() {
        val breedDataResponse : BreedDataResponse = LookupRepository().getAllBreedData(12)
        println("returned! " + breedDataResponse.breedDataList?.size)
        println("paginationCount=" + breedDataResponse.paginationCount)
        println("paginationPage=" + breedDataResponse.paginationPage)
        println("paginationLimit=" + breedDataResponse.paginationLimit)
    }

    @Test
    fun getImage() {
        val imageDataResponse : ImageDataResponse = LookupRepository().getImageData("acur")
        println("returned! " + imageDataResponse.imageDataList?.size)
        println("paginationCount=" + imageDataResponse.paginationCount)
        println("paginationPage=" + imageDataResponse.paginationPage)
        println("paginationLimit=" + imageDataResponse.paginationLimit)

        println("selected breed id: " + imageDataResponse.imageDataList?.get(0)?.breeds?.get(0)?.id)
        println("selected breed name: " + imageDataResponse.imageDataList?.get(0)?.breeds?.get(0)?.name)
        println("selected breed alt names: " + imageDataResponse.imageDataList?.get(0)?.breeds?.get(0)?.alt_names)
        println("selected breed alt url: " + imageDataResponse.imageDataList?.get(0)?.url)
    }

    @Test
    fun loadImageFromUrl() {
        try {
            val urlString = "https://cdn2.thecatapi.com/images/_6x-3TiCA.jpg"

            var urlConnection: HttpURLConnection = URL(urlString).openConnection() as HttpURLConnection
            urlConnection.doInput = true
            urlConnection.connect()
            var inputStream: InputStream = urlConnection.getInputStream()
            var bufferedInputStream: BufferedInputStream = BufferedInputStream(inputStream)
            var bitmap: Bitmap = BitmapFactory.decodeStream(bufferedInputStream)


            if (bitmap != null) {
                println("not null!")
            }
        } catch (throwable: Throwable) {
            println("error! " + throwable.message)
        }
    }
}