package com.ateittinen.example.catbreedlookup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Author: Anna Teittinen
 * Date: August 22, 2020
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.layoutForFragment, LookupFragment())
            .commit()
    }


}