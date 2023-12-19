package com.newapp.cryptocurrencytestapp.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.newapp.cryptocurrencytestapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .add(R.id.content, CryptocurrencyFragment())
            .commit()
    }

}