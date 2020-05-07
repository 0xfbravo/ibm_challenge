package com.ibm.challenge.login.domain.interactors

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidateCpfTest {

    private var validateCpf = ValidateCpf()

    @Test
    fun testCpfWhenFullPattern_ShouldReturnSuccess() {
        assertTrue(validateCpf.withParams("15939427707").execute())
        assertTrue(validateCpf.withParams("159.394.277-07").execute())
    }

    @Test
    fun testCpfWhenEmpty_ShouldReturnError() {
        assertFalse(validateCpf.withParams("").execute())
    }

    @Test
    fun testCpfWhenWrongLength_ShouldReturnError() {
        assertFalse(validateCpf.withParams("1234").execute())
    }

    @Test
    fun testCpfWhenIsOnBlackList_ShouldReturnError() {
        assertFalse(validateCpf.withParams("00000000000").execute())
        assertFalse(validateCpf.withParams("11111111111").execute())
        assertFalse(validateCpf.withParams("22222222222").execute())
        assertFalse(validateCpf.withParams("33333333333").execute())
        assertFalse(validateCpf.withParams("44444444444").execute())
        assertFalse(validateCpf.withParams("55555555555").execute())
        assertFalse(validateCpf.withParams("66666666666").execute())
        assertFalse(validateCpf.withParams("77777777777").execute())
        assertFalse(validateCpf.withParams("88888888888").execute())
        assertFalse(validateCpf.withParams("99999999999").execute())
    }

    @Test
    fun testCpfWhenFistPartOfAlgorithmIsWrong_ShouldReturnError() {
        assertFalse(validateCpf.withParams("15939427717").execute())
    }

    @Test
    fun testCpfWhenSecondPartOfAlgorithmIsWrong_ShouldReturnError() {
        assertFalse(validateCpf.withParams("159394277079").execute())
    }

    @Test
    fun testCpfWhenHasAWord_ShouldReturnError() {
        assertFalse(validateCpf.withParams("l593942ff").execute())
    }

}