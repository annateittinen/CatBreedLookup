package com.ateittinen.example.catbreedlookup.presentation

import com.ateittinen.example.catbreedlookup.domain.BreedDataList
import com.ateittinen.example.catbreedlookup.domain.ImageDataList
import com.ateittinen.example.catbreedlookup.domain.LookupUseCase
import com.ww.roxie.BaseViewModel
import com.ww.roxie.Reducer
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

/**
 * Author: Anna Teittinen
 * Date: August 22, 2020
 */
class LookupViewModel(
    initialState: State?,
    private val lookupUseCase: LookupUseCase
) : BaseViewModel<Action, State>() {
    override val initialState = initialState ?: State.Idle

    private var breedDataList: BreedDataList = mutableListOf()
    private var nextPage: Int = 0

    private var imageDataList: ImageDataList = mutableListOf()

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.LoadingBreeds -> {
                State.LoadingBreeds
            }
            is Change.LoadedBreeds -> {
                State.LoadedBreeds(change.breedDataList)
            }
            is Change.LoadBreedsError -> {
                State.LoadingBreedsError(change.throwable)
            }

            is Change.LoadingImage -> {
                State.LoadingImage
            }
            is Change.LoadedImage -> {
                State.LoadedImage(change.imageDataList)
            }
            is Change.LoadImageError -> {
                State.LoadingImageError(change.throwable)
            }
        }
    }

    init {
        bindActions()
    }

    private fun bindActions() {
        val loadBreedsObservable = actions.ofType<Action.LoadBreeds>()
            .switchMap {
                Single.fromCallable {
                    lookupUseCase.getBreeds(++nextPage)
                }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toObservable()
                    .map<Change> {
                        breedDataList = it.blockingGet().breedDataList!!
                        Change.LoadedBreeds(breedDataList)
                    }
                    .onErrorReturn {
                        Change.LoadBreedsError(it)
                    }
                    .startWith(Change.LoadingBreeds)
            }

        val loadImageObservable = actions.ofType<Action.LoadImage>()
            .switchMap {
                Single.fromCallable {
                    lookupUseCase.getImage(it.breedId)
                }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toObservable()
                    .map<Change> {
                        imageDataList = it.blockingGet().imageDataList!!
                        Change.LoadedImage(imageDataList)
                    }
                    .onErrorReturn {
                        Change.LoadImageError(it)
                    }
                    .startWith(Change.LoadingImage)
            }

        val allChanges = Observable.merge(loadBreedsObservable, loadImageObservable)

        disposables += allChanges
            .scan(initialState, reducer)
            .filter {
                it != State.Idle
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::setValue)
    }

}
