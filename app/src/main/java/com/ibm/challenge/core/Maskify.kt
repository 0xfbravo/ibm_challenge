package com.ibm.challenge.core

import com.redmadrobot.inputmask.helper.Mask
import com.redmadrobot.inputmask.model.CaretString

object Maskify {

    enum class Type(val pattern: String) {
        CPF("[000].[000].[000]-[00]")
    }

    fun with(text: String, type: Type): String {
        val caretString = CaretString(text, 0, CaretString.CaretGravity.FORWARD(false))

        return when (type) {
            Type.CPF -> Mask(type.pattern).apply(caretString).formattedText.string
            else -> text
        }
    }

}