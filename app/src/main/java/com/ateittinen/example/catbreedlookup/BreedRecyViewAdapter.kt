package com.ateittinen.example.catbreedlookup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ateittinen.example.catbreedlookup.domain.BreedData
import com.ateittinen.example.catbreedlookup.domain.BreedDataList
import java.lang.ref.WeakReference

/**
 * Author: Anna Teittinen
 * Date: August 22, 2020
 */
class SingleBreedRecyViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val singleBreedLayout = view
    val nameValueTextView = singleBreedLayout.findViewById<TextView>(R.id.nameValueTextView)
    val altNameValueTextView = singleBreedLayout.findViewById<TextView>(R.id.altNameValueTextView)
}


class BreedRecyViewAdapter(lookupFragment: LookupFragment): RecyclerView.Adapter<SingleBreedRecyViewHolder>() {

    private val weakReferenceLookupFragment: WeakReference<LookupFragment> = WeakReference<LookupFragment>(lookupFragment)

    private var breedDataList: BreedDataList = mutableListOf()

    fun addToList(moreBreedsList: BreedDataList) {
        if (!moreBreedsList.isEmpty())
            breedDataList.addAll(moreBreedsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleBreedRecyViewHolder {

        return SingleBreedRecyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.single_breed, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SingleBreedRecyViewHolder, position: Int) {
        val breedData : BreedData = breedDataList.get(position)
        holder.nameValueTextView.text = breedData.name
        holder.altNameValueTextView.text = breedData.alt_names

        holder.singleBreedLayout.setOnClickListener {
            (weakReferenceLookupFragment.get() as LookupFragment).getBreedData(breedData)
        }
    }

    override fun getItemCount(): Int = breedDataList.size

}