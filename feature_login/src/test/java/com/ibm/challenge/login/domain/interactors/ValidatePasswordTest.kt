package com.ibm.challenge.login.domain.interactors

import org.junit.Test

import org.junit.Assert.*

class ValidatePasswordTest {

    private var validatePassword = ValidatePassword()

    @Test
    fun testPasswordWhenFullPattern_ShouldReturnSuccess() {
        assertTrue(validatePassword.withParams("Abc123@").execute())
        assertTrue(validatePassword.withParams("Test@1").execute())
        assertTrue(validatePassword.withParams("Ding#Dong1").execute())
        assertTrue(validatePassword.withParams("Ab\$c123").execute())
        assertTrue(validatePassword.withParams("A!2").execute())
        assertTrue(validatePassword.withParams("C%3").execute())
        assertTrue(validatePassword.withParams("3*E").execute())
        assertTrue(validatePassword.withParams("Https2??").execute())
        assertTrue(validatePassword.withParams("D&D3").execute())
    }

    @Test
    fun testPasswordWhenMissingUppercase_ShouldReturnError() {
        assertFalse(validatePassword.withParams("abc123@").execute())
    }

    @Test
    fun testPasswordWhenMissingNumbers_ShouldReturnError() {
        assertFalse(validatePassword.withParams("abc@").execute())
    }

    @Test
    fun testPasswordWhenMissingSpecialChar_ShouldReturnError() {
        assertFalse(validatePassword.withParams("abc123").execute())
    }

    @Test
    fun testPasswordWhenEmpty_ShouldReturnError() {
        assertFalse(validatePassword.withParams("").execute())
    }

}