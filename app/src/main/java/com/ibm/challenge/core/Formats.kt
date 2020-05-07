package com.ibm.challenge.core

import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object Formats {

    val currencyFormatter: NumberFormat
        get() = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    val statementCardFormat: DateFormat
        get() = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)

}