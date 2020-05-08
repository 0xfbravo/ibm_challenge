package com.ibm.challenge.data.repository.security

import android.os.Build
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.security.SecureRandom

@RunWith(AndroidJUnit4::class)
@SmallTest
class CipherWrapperTest {
    private val asymmetricKeyAlias = "rsaasymkey"
    private val symmetricKeyAlias = "aesasymkey"
    private val smallDataToEncrypt = ByteArray(1)
    private val largeDataToEncrypt = ByteArray(98320*200)

    private lateinit var cipherWrapper: CipherWrapper
    private lateinit var keystoreRepository: KeystoreRepositoryImpl

    @Before
    fun init() {
        Log.i(CipherWrapper.TAG,"Running CipherWrapper on API ${Build.VERSION.SDK_INT}")
        cipherWrapper = CipherWrapper()
        keystoreRepository = KeystoreRepositoryImpl(InstrumentationRegistry.getInstrumentation().context, cipherWrapper)
        keystoreRepository.removeAllKeys()

        val secureRandom = SecureRandom()
        secureRandom.nextBytes(largeDataToEncrypt)
    }

    @After
    fun removeKeys() {
        keystoreRepository.removeKey(symmetricKeyAlias)
        keystoreRepository.removeKey(asymmetricKeyAlias)
        keystoreRepository.removeAllKeys()
    }

    @Test
    fun testLargeDataSymmetricKeyEncryptionSuccess() {
        /* Test keystore key retrieve */
        val symmetricKeyGenerated = keystoreRepository.generateSymmetricKey(symmetricKeyAlias)
        val symmetricKeyRetrieved = keystoreRepository.getSymmetricKey(symmetricKeyAlias)
        assert(symmetricKeyGenerated == symmetricKeyRetrieved!!)

        /* Test encryption */
        val encrypted = cipherWrapper.encryptAES(largeDataToEncrypt, symmetricKeyGenerated)
        val decrypted = cipherWrapper.decryptAES(encrypted, symmetricKeyGenerated)
        assert(decrypted.contentEquals(largeDataToEncrypt))
    }

    @Test
    fun testSmallDataSymmetricKeyEncryptionSuccess() {
        /* Test keystore key retrieve */
        val symmetricKeyGenerated = keystoreRepository.generateSymmetricKey(symmetricKeyAlias)
        val symmetricKeyRetrieved = keystoreRepository.getSymmetricKey(symmetricKeyAlias)
        assert(symmetricKeyGenerated == symmetricKeyRetrieved!!)

        /* Test encryption */
        val encrypted = cipherWrapper.encryptAES(smallDataToEncrypt, symmetricKeyGenerated)
        val decrypted = cipherWrapper.decryptAES(encrypted, symmetricKeyGenerated)
        assert(decrypted.contentEquals(smallDataToEncrypt))
    }
}