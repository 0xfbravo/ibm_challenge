package com.ibm.challenge.login.domain.interactors

import org.junit.Test

import org.junit.Assert.*

class ValidateEmailTest {

    private var validateEmail = ValidateEmail()

    @Test
    fun testEmailWhenFullPattern_ShouldReturnSuccess() {
        assertTrue(validateEmail.withParams("fellipe.bravo@gmail.com").execute())
        assertTrue(validateEmail.withParams("dev.fbrp@gmail.com").execute())
        assertTrue(validateEmail.withParams("ding@dong.com").execute())
        assertTrue(validateEmail.withParams("challenge@ibm.com").execute())
        assertTrue(validateEmail.withParams("abc@email.com.br").execute())
        assertTrue(validateEmail.withParams("abc123@email.com.br").execute())
        assertTrue(validateEmail.withParams("123@email.com.br").execute())
        assertTrue(validateEmail.withParams("f@b.c").execute())
    }

    @Test
    fun testEmailWhenEmpty_ShouldReturnSuccess() {
        assertFalse(validateEmail.withParams("").execute())
    }

    @Test
    fun testEmailWhenHasSpecialChar_ShouldReturnSuccess() {
        assertFalse(validateEmail.withParams("fellipe()bravo@gmail.com").execute())
        assertFalse(validateEmail.withParams("fellipe@bravo@gmail.com").execute())
        assertFalse(validateEmail.withParams("fellipe.bravo@gmail,com").execute())
    }

    @Test
    fun testEmailWithoutDomain_ShouldReturnSuccess() {
        assertFalse(validateEmail.withParams("fellipe()bravo@").execute())
    }

}