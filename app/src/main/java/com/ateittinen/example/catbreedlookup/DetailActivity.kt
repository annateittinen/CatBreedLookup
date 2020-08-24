package com.ateittinen.example.catbreedlookup

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ateittinen.example.catbreedlookup.domain.BreedData
import com.ateittinen.example.catbreedlookup.domain.ImageData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.URL
import java.net.URLConnection

/**
 * Author: Anna Teittinen
 * Date: August 22, 2020
 */
class DetailActivity : AppCompatActivity() {

    private var imageUrl = ""
    private lateinit var imageView_catBreedImage: ImageView
    lateinit var toastLoadingImageFromUrl: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val bundle: Bundle? = intent.extras
        val imageData: ImageData? = bundle?.getParcelable("imageData")

        imageView_catBreedImage = findViewById(R.id.imageView_catBreedImage)

        val nameValueTextView = findViewById<TextView>(R.id.nameValueTextView)
        val altNameValueTextView = findViewById<TextView>(R.id.altNameValueTextView)
        val descriptionValueLabel = findViewById<TextView>(R.id.descriptionValueLabel)
        val temperamentValueLabel = findViewById<TextView>(R.id.temperamentValueLabel)
        val lifeSpanValueLabel = findViewById<TextView>(R.id.lifeSpanValueLabel)
        val originValueLabel = findViewById<TextView>(R.id.originValueLabel)
        val weightValueLabel = findViewById<TextView>(R.id.weightValueLabel)
        val countryCodeValueLabel = findViewById<TextView>(R.id.countryCodeValueLabel)

        val breedData : BreedData? = imageData?.breeds?.get(0)
        if ((imageData?.url != null) && (imageData?.url?.length > 0))
            imageUrl = imageData.url

        toastLoadingImageFromUrl = Toast.makeText(this, "Loading image...", Toast.LENGTH_LONG)
        toastLoadingImageFromUrl.setGravity(Gravity.CENTER_VERTICAL, 0, 0)

        descriptionValueLabel.text = breedData?.description
        nameValueTextView.text = breedData?.name
        altNameValueTextView.text = breedData?.alt_names
        temperamentValueLabel.text = breedData?.temperament
        lifeSpanValueLabel.text = breedData?.life_span
        originValueLabel.text = breedData?.origin
        weightValueLabel.text = breedData?.weight?.imperial.toString() + " lbs"
        countryCodeValueLabel.text = breedData?.country_code
    }

    override fun onResume() {
        super.onResume()

        toastLoadingImageFromUrl.show()

        Single.fromCallable {
            loadImageFromUrl(imageUrl)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    //on success
                    toastLoadingImageFromUrl.cancel()
                    imageView_catBreedImage.setImageBitmap(it.blockingGet())},
                {
                    //on failure
                    toastLoadingImageFromUrl.cancel()
                    showErrorDialog(it)}
            )
    }

    private fun loadImageFromUrl(url: String) : Single<Bitmap> {

        try {
            val urlConnection: URLConnection = URL(url).openConnection()
            urlConnection.connect()
            val inputStream: InputStream = urlConnection.getInputStream()
            val bufferedInputStream: BufferedInputStream = BufferedInputStream(inputStream)
            val bitmap: Bitmap = BitmapFactory.decodeStream(bufferedInputStream)
            return Single.just(bitmap)
        } catch (throwable: Throwable) {
            throw throwable
        }
    }

    private fun showErrorDialog(throwable: Throwable) {
        AlertDialog.Builder(this).setMessage(throwable.message)
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, i ->
                dialog.cancel()
            })
            .create().show()
    }
}