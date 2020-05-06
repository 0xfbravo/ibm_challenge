package com.ibm.challenge.login.domain.interactors

import org.junit.Test

import org.junit.Assert.*

class ValidateCPFTest {

    private var validateCPF = ValidateCPF()

    @Test
    fun testCpfWhenFullPattern_ShouldReturnSuccess() {
        assertTrue(validateCPF.withParams("15939427707").execute())
        assertTrue(validateCPF.withParams("159.394.277-07").execute())
    }

    @Test
    fun testCpfWhenEmpty_ShouldReturnError() {
        assertFalse(validateCPF.withParams("").execute())
    }

    @Test
    fun testCpfWhenWrongLength_ShouldReturnError() {
        assertFalse(validateCPF.withParams("1234").execute())
    }

    @Test
    fun testCpfWhenIsOnBlackList_ShouldReturnError() {
        assertFalse(validateCPF.withParams("00000000000").execute())
        assertFalse(validateCPF.withParams("11111111111").execute())
        assertFalse(validateCPF.withParams("22222222222").execute())
        assertFalse(validateCPF.withParams("33333333333").execute())
        assertFalse(validateCPF.withParams("44444444444").execute())
        assertFalse(validateCPF.withParams("55555555555").execute())
        assertFalse(validateCPF.withParams("66666666666").execute())
        assertFalse(validateCPF.withParams("77777777777").execute())
        assertFalse(validateCPF.withParams("88888888888").execute())
        assertFalse(validateCPF.withParams("99999999999").execute())
    }

    @Test
    fun testCpfWhenFistPartOfAlgorithmIsWrong_ShouldReturnError() {
        assertFalse(validateCPF.withParams("15939427717").execute())
    }

    @Test
    fun testCpfWhenSecondPartOfAlgorithmIsWrong_ShouldReturnError() {
        assertFalse(validateCPF.withParams("159394277079").execute())
    }

    @Test
    fun testCpfWhenHasAWord_ShouldReturnError() {
        assertFalse(validateCPF.withParams("l593942ff").execute())
    }

}