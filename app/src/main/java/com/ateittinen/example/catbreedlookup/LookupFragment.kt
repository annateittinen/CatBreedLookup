package com.ateittinen.example.catbreedlookup

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ateittinen.example.catbreedlookup.data.LookupRepository
import com.ateittinen.example.catbreedlookup.domain.BreedData
import com.ateittinen.example.catbreedlookup.domain.ImageData
import com.ateittinen.example.catbreedlookup.domain.LookupUseCase
import com.ateittinen.example.catbreedlookup.presentation.Action
import com.ateittinen.example.catbreedlookup.presentation.LookupViewModel
import com.ateittinen.example.catbreedlookup.presentation.LookupViewModelFactory
import com.ateittinen.example.catbreedlookup.presentation.State

/**
 * Author: Anna Teittinen
 * Date: August 22, 2020
 */
class LookupFragment : Fragment() {

    private lateinit var viewModel: LookupViewModel

    private lateinit var recyView: RecyclerView
    private lateinit var recyViewLayoutManager: RecyclerView.LayoutManager
    private lateinit var recyViewAdapter: BreedRecyViewAdapter

    private lateinit var toastLoadingBreeds: Toast
    private lateinit var toastLoadingBreedDetails: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentLayout =  inflater.inflate(R.layout.fragment_lookup, container, false)

        recyView = fragmentLayout.findViewById(R.id.breedsRecyView)
        recyViewLayoutManager = LinearLayoutManager(activity)
        recyViewAdapter = BreedRecyViewAdapter(this)
        recyView.apply {
            layoutManager = recyViewLayoutManager
            adapter = recyViewAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!canScrollVertically(1)) {
                        viewModel.dispatch(Action.LoadBreeds)
                    }
                }
            })
        }

        toastLoadingBreeds = Toast.makeText(activity, "Looking up breeds...", Toast.LENGTH_LONG)
        toastLoadingBreeds.setGravity(Gravity.CENTER_VERTICAL, 0, 0)

        toastLoadingBreedDetails = Toast.makeText(activity, "Looking up details...", Toast.LENGTH_LONG)
        toastLoadingBreedDetails.setGravity(Gravity.CENTER_VERTICAL, 0, 0)

        return fragmentLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this,
            LookupViewModelFactory(null, LookupUseCase(LookupRepository()))
        )
            .get(LookupViewModel::class.java)

        viewModel.observableState.observe(this,
            Observer { state ->
                state?.let {
                    renderState(state)
                }
            }
        )

        viewModel.dispatch(Action.LoadBreeds)
    }

    private fun renderState(state: State) {
        when (state) {
            is State.LoadingBreeds -> {
                toastLoadingBreeds.show()
            }
            is State.LoadedBreeds -> {
                recyViewAdapter.addToList(state.breedsDataList)
                toastLoadingBreeds.cancel()
            }
            is State.LoadingBreedsError -> {
                toastLoadingBreeds.cancel()
                AlertDialog.Builder(context).setMessage(state.throwable.message)
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, i ->
                        dialog.cancel()
                    })
                    .create().show()
            }

            is State.LoadingImage -> {
                toastLoadingBreedDetails.show()
            }
            is State.LoadedImage -> {
                // Launch activity to show details!
                val imageData: ImageData = state.imageDataList.get(0)
                startDetailActivity(imageData)
                toastLoadingBreedDetails.cancel()
            }
            is State.LoadingImageError -> {
                toastLoadingBreedDetails.cancel()
                AlertDialog.Builder(context).setMessage(state.throwable.message)
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, i ->
                        dialog.cancel()
                    })
                    .create().show()
            }
        }
    }

    fun getBreedData(breedData: BreedData) {
        viewModel.dispatch(Action.LoadImage(breedData.id))
    }

    private fun startDetailActivity(imageData: ImageData) {
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtras(Bundle().apply {
                putParcelable("imageData", imageData)
            })
        }
        val requestCode = 1
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}