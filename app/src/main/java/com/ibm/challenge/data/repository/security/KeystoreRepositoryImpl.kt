package com.ibm.challenge.data.repository.security

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import com.ibm.challenge.domain.repository.security.KeystoreRepository
import java.io.IOException
import java.math.BigInteger
import java.security.*
import java.security.KeyStore.getInstance
import java.security.interfaces.RSAPublicKey
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import javax.security.auth.x500.X500Principal

class KeystoreRepositoryImpl(private val context: Context,
                             private val cipherWrapper: CipherWrapper): KeystoreRepository {

    private val androidKeyStore = "AndroidKeyStore"
    private val keyStore: KeyStore = getInstance(androidKeyStore)

    /* KeyStore Defaults */
    private val deviceAsymmetricKeyAlias = "deviceAsymmetricKey"
    private val privateSharedPreferences = "keystoreRepositorySP"

    companion object {
        const val TAG = "keystore_repository"
        const val aesKeySize = 256
        const val rsaKeySize = 2048
    }

    init {
        keyStore.load(null)

        /*
        * Check if has already generated deviceAsymmetricKey, if not, generates it
        * exclusively for API < 23 to encrypt AES Key
        */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) { // Prevents method execution on other APIs < 23
            if (getDeviceAsymmetricKey() == null) {
                generateAsymmetricKey(deviceAsymmetricKeyAlias)
            }
        }
    }

    /**
     * Retrieves an asymmetric key from Android KeyStore
     * that's represents device identification to world.
     */
    private fun getDeviceAsymmetricKey(): KeyPair? {
        return getAsymmetricKey(deviceAsymmetricKeyAlias)
    }

    /**
     * Retrieves a symmetric key from Android KeyStore
     * or from Shared Preferences, if API < 23
     */
    fun getSymmetricKey(alias: String): SecretKey? {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            val sharedPreferences = context.getSharedPreferences(privateSharedPreferences, Context.MODE_PRIVATE)
            val wrappedKey = sharedPreferences.getString(alias, null)
            wrappedKey?.let {
                val deviceAsymmetricKey = getDeviceAsymmetricKey()
                val decodedKey = Base64.decode(it, Base64.DEFAULT)
                val unwrappedKey = cipherWrapper.decryptRSA(decodedKey, deviceAsymmetricKey!!.private)

                Log.w(TAG, "Symmetric key with alias: $alias retrieved from SharedPreferences :: [$unwrappedKey]")

                val aesKey = SecretKeySpec(unwrappedKey.encoded, CipherWrapper.AES)
                return aesKey
            }
        }
        else {
            val secretKey = keyStore.getKey(alias, null) as SecretKey?
            Log.d(TAG, "Symmetric key with alias: $alias retrieved from Android KeyStore :: [$secretKey]")
            secretKey
        }
    }

    /**
     * Retrieves a asymmetric key from Android KeyStore
     */
    fun getAsymmetricKey(alias: String): KeyPair? {
        return try {
            val privateKey = keyStore.getKey(alias, null) as PrivateKey?
            val publicKey = keyStore.getCertificate(alias)?.publicKey

            Log.d(TAG, "Asymmetric key with alias: $alias retrieved :: [$publicKey] [$privateKey]")

            if (privateKey != null && publicKey != null) {
                KeyPair(publicKey, privateKey)
            } else {
                null
            }
        }
        catch (e : Exception) {
            Log.e(TAG, "Asymmetric key with alias: $alias ${e.localizedMessage}")
            null
        }
    }

    /**
     * When the API is lower than 23 its necessary to generate the key
     * using KeyPairGenerator with some especial parameters.
     * By default, we use RSA/ECB/OAEPwithSHA-256andMGF1Padding for ASYMMETRIC KEYS
     */
    fun generateAsymmetricKey(alias: String): KeyPair {
        val start = Calendar.getInstance()
        val end = Calendar.getInstance()
        end.add(Calendar.YEAR, 30)

        val kpgSpecs = KeyPairGeneratorSpec.Builder(context)
                .setKeySize(rsaKeySize)
                .setAlias(alias)
                .setSubject(X500Principal("CN=$alias"))
                .setSerialNumber(BigInteger.TEN)
                .setStartDate(start.time)
                .setEndDate(end.time)
        val keyPairGenerator = KeyPairGenerator.getInstance(CipherWrapper.RSA, androidKeyStore)
        keyPairGenerator.initialize(kpgSpecs.build())
        val keyPair = keyPairGenerator.generateKeyPair()

        Log.d(TAG, "New asymmetric key created for alias: $alias :: [${keyPair.public}] [${keyPair.private}]")

        return keyPair
    }

    override fun generateSymmetricKey(alias: String, userAuthenticationRequired: Boolean,
                             invalidatedByBiometricEnrollment: Boolean,
                             userAuthenticationValidityDurationSeconds: Int,
                             userAuthenticationValidWhileOnBody: Boolean): SecretKey {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            generateSymmetricKeyLessThanApi23(alias)
        else
            generateSymmetricKeyMoreThanApi23(alias, userAuthenticationRequired,
                    invalidatedByBiometricEnrollment, userAuthenticationValidityDurationSeconds,
                    userAuthenticationValidWhileOnBody)
    }

    /**
     * When the API is lower than 23 its necessary to generate the key
     * using SecureRandom, encrypt it using default RSA key then store
     * it on SharedPreferences.
     * By default, we use AES/ECB/PKCS7Padding for SYMMETRIC KEYS
     */
    @Throws(IOException::class)
    private fun generateSymmetricKeyLessThanApi23(alias: String): SecretKey {
        val sharedPreferences = context.getSharedPreferences(privateSharedPreferences, Context.MODE_PRIVATE)

        /* Key was generated previously */
        if (sharedPreferences.contains(alias))
            return getSymmetricKey(alias)!!

        /* Key wasn't generated yet */
        val keyByteArray = ByteArray(32) // 256 Bits
        val secureRandom = SecureRandom()
        secureRandom.nextBytes(keyByteArray)
        val secretKey = SecretKeySpec(keyByteArray, CipherWrapper.AES)

        // Wraps secret key with device asymmetric key
        val deviceAsymmetricKey = getDeviceAsymmetricKey()
        val wrappedKey = cipherWrapper.encryptRSA(secretKey, deviceAsymmetricKey!!.public as RSAPublicKey)

        // Stores on Shared Preferences the secret key encrypted
        val edit = sharedPreferences.edit()
        edit.putString(alias, Base64.encodeToString(wrappedKey, Base64.DEFAULT))
        val successfullyWroteKey = edit.commit()

        if (successfullyWroteKey)
            Log.d(TAG, "Key: $alias saved on SharedPreferences.")
        else
            throw IOException("Could not save keys")

        Log.d(TAG, "New symmetric key created for alias: $alias on API 23- :: [$secretKey]")

        return secretKey
    }

    /**
     * When on API 23 or higher its necessary to generate the key
     * using KeyGenerator with some especial parameters.
     * By default, we use AES/GCM/NoPadding for SYMMETRIC KEYS
     */
    @TargetApi(Build.VERSION_CODES.M)
    private fun generateSymmetricKeyMoreThanApi23(alias: String,
                                                  userAuthenticationRequired: Boolean = false,
                                                  invalidatedByBiometricEnrollment: Boolean = true,
                                                  userAuthenticationValidityDurationSeconds: Int = -1,
                                                  userAuthenticationValidWhileOnBody: Boolean = true): SecretKey {

        /* Key was generated previously */
        if (keyStore.containsAlias(alias))
            return getSymmetricKey(alias)!!

        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, androidKeyStore)
        val builder = KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setKeySize(aesKeySize)
                // Require the user to authenticate with a fingerprint to authorize every use of the key
                .setUserAuthenticationRequired(userAuthenticationRequired)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setUserAuthenticationValidityDurationSeconds(userAuthenticationValidityDurationSeconds)
                .setRandomizedEncryptionRequired(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setInvalidatedByBiometricEnrollment(invalidatedByBiometricEnrollment)
            builder.setUserAuthenticationValidWhileOnBody(userAuthenticationValidWhileOnBody)
        }
        keyGenerator.init(builder.build())

        val secretKey = keyGenerator.generateKey()
        keyStore.setKeyEntry(alias, secretKey, null, null)

        Log.d(TAG, "New symmetric key created for alias: $alias on API 23+ :: [$secretKey]")

        return secretKey
    }

    /**
     * Removes all stored keys from Android KeyStore (API >= 23)
     * and SharedPreferences (API < 23)
     */
    fun removeAllKeys() {
        // Remove all from Android KeyStore
        keyStore.deleteEntry(deviceAsymmetricKeyAlias)

        // Remove all from SharedPreferences
        val sharedPreferences = context.getSharedPreferences(privateSharedPreferences, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        Log.d(TAG, "All keys were removed from Android KeyStore and SharedPreferences.")
    }

    /**
     * Removes a single stored key from Android KeyStore (API >= 23)
     * and SharedPreferences (API < 23)
     */
    fun removeKey(alias: String) {
        // Remove all from Android KeyStore
        keyStore.deleteEntry(alias)

        // Remove all from SharedPreferences
        val sharedPreferences = context.getSharedPreferences(privateSharedPreferences, Context.MODE_PRIVATE)
        sharedPreferences.edit().remove(alias).apply()

        Log.d(TAG, "The key with alias [$alias] were removed from Android KeyStore and SharedPreferences.")
    }

}