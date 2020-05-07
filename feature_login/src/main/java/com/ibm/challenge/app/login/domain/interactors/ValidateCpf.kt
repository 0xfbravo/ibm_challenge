package com.ibm.challenge.app.login.domain.interactors

import com.ibm.challenge.domain.interactos.Interactor

class ValidateCpf: Interactor<Boolean>() {

    private val cpfLength = 11
    private val notNumbersRegex = Regex("\\D")
    private val formattedCpfRegex = Regex("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
    private val cleanCpfRegex = Regex("\\d{11}")
    private var cpf: String? = null

    override fun execute(): Boolean {
        if (cpf.isNullOrEmpty())
            return false

        cpf?.let {
            val formattedCPFMatches = formattedCpfRegex.matches(it)
            val cleanCPFMatches = cleanCpfRegex.matches(it)

            if (!formattedCPFMatches && !cleanCPFMatches)
                return false

            val cleanCPF = it.replace(notNumbersRegex,"").trim()

            /* Check CPF length */
            if (cleanCPF.length != cpfLength)
                return false

            /* Check if CPF is on Blacklist */
            if (isOnBlackList(cleanCPF))
                return false

            /* Turns string into IntArray (reversed) */
            val cpfIntArray = cleanCPF
                .map { cpfDigit -> cpfDigit.toString().toInt() }
                .toIntArray()

            /* Get the most priority digits to compare later */
            val nextToLast = cpfIntArray[9]
            val lastDigit = cpfIntArray[10]

            /* CPF Algorithm - 1st Step */
            var sum = 0
            for (i in 0 until 9) sum += cpfIntArray[i] * (10 - i)
            var expectedDigit = sum % 11
            expectedDigit = if (expectedDigit < 2) 0 else 11 - expectedDigit
            val firstStepDigitMatch = expectedDigit == nextToLast

            if (!firstStepDigitMatch)
                return false

            /* CPF Algorithm - 2nd Step */
            sum = 0
            for (i in 0 until 10) sum += cpfIntArray[i] * (11 - i)
            expectedDigit = sum % 11
            expectedDigit = if (expectedDigit < 2) 0 else 11 - expectedDigit
            val secondStepDigitMatch = expectedDigit == lastDigit

            if (!secondStepDigitMatch)
                return false

            return true
        }

        return false
    }

    fun withParams(documentNumber: String): ValidateCpf {
        this.cpf = documentNumber.trim()
        return this
    }

    /**
     * Compare a text to a invalid numbers list
     */
    private fun isOnBlackList(text: String): Boolean { return (0..9).map { it.toString().repeat(cpfLength) }.map { text == it }.any { it } }

}