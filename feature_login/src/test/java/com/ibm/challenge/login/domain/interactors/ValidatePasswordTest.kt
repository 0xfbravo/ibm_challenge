package com.ibm.challenge.login.domain.interactors

import org.junit.Test

import org.junit.Assert.*

class ValidatePasswordTest {

    private var validatePassword = ValidatePassword()

    @Test
    fun testPasswordFullPatternSuccess() {
        assert(validatePassword.withParams("Abc123@").execute())
        assert(validatePassword.withParams("Test@1").execute())
        assert(validatePassword.withParams("Ding#Dong1").execute())
        assert(validatePassword.withParams("Ab\$c123").execute())
        assert(validatePassword.withParams("A!2").execute())
        assert(validatePassword.withParams("C%3").execute())
        assert(validatePassword.withParams("3*E").execute())
        assert(validatePassword.withParams("Https2??").execute())
        assert(validatePassword.withParams("D&D3").execute())
    }

    @Test
    fun testPasswordMissingUppercaseError() {
        assert(!validatePassword.withParams("abc123@").execute())
    }

    @Test
    fun testPasswordMissingNumbersError() {
        assert(!validatePassword.withParams("abc@").execute())
    }

    @Test
    fun testPasswordMissingSpecialCharError() {
        assert(!validatePassword.withParams("abc123").execute())
    }

}