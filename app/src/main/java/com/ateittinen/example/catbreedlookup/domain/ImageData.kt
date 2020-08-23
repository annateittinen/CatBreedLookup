package com.ateittinen.example.catbreedlookup.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Author: Anna Teittinen
 * Date: August 22, 2020
 */

typealias ImageDataList = MutableList<ImageData>

@Parcelize
data class ImageData(
    val breeds: List<BreedData>,
    val id: String,  //this is not BreedData.id !
    val height: Long,
    val width: Long,
    val url: String
) : Parcelable