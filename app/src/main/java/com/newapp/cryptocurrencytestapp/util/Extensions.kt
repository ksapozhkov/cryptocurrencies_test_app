package com.newapp.cryptocurrencytestapp.util

import kotlin.math.roundToInt

object Extensions {

    fun Double.formatPrice(): String {
        val roundedValue: Double = (this * 100.0).roundToInt() / 100.0
        return "$" + if ((roundedValue * 100 % 10) > 0) {
            roundedValue.toString()
        } else roundedValue.toString() + "0"
    }

}