package com.ibm.challenge.core

import java.text.NumberFormat
import java.util.*

object Formats {

    val currencyFormatter: NumberFormat
        get() = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

}