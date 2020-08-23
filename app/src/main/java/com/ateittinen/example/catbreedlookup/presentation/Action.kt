package com.ateittinen.example.catbreedlookup.presentation

import com.ww.roxie.BaseAction

/**
 * Author: Anna Teittinen
 * Date: August 22, 2020
 */
sealed class Action: BaseAction {
    object LoadBreeds: Action()
    data class LoadImage(val breedId: String): Action()
}