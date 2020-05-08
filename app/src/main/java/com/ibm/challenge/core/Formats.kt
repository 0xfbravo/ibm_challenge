package com.ibm.challenge.core

import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Formats {

    val currencyFormatter: DecimalFormat
        get() {
            val format = DecimalFormat.getCurrencyInstance(Locale("pt", "BR")) as DecimalFormat
            format.apply {
                negativePrefix = "- R$ "
                negativeSuffix = ""
                positivePrefix = "R$ "
                positiveSuffix = ""
            }
            return format
        }

    val statementCardFormat: DateFormat
        get() = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)

}