package com.ibm.challenge.domain.repository.security

import javax.crypto.SecretKey

interface KeystoreRepository {

    fun generateSymmetricKey(alias: String,
                             userAuthenticationRequired: Boolean = false,
                             invalidatedByBiometricEnrollment: Boolean = true,
                             userAuthenticationValidityDurationSeconds: Int = -1,
                             userAuthenticationValidWhileOnBody: Boolean = true): SecretKey

}