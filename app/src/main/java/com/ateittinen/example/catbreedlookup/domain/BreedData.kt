package com.ateittinen.example.catbreedlookup.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Author: Anna Teittinen
 * Date: August 22, 2020
 */

typealias BreedDataList = MutableList<BreedData>

@Parcelize
data class Weight(
    val imperial: String, val metric: String
) : Parcelable

/**
 * Included only the some of the fields.
 */
@Parcelize
data class BreedData(
    val id: String,  //4 chars
    val name: String,
    val temperament: String,
    val life_span: String,
    val alt_names: String = "", //can be 0 chars
    val description: String,
    val origin: String,
    val weight: Weight,
    val country_code: String
) : Parcelable