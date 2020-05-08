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

@RunWith(AndroidJUnit4::class)
@SmallTest
class KeystoreRepositoryImplTest {

    private val invalidKeyAlias = "invalid_key_alias"
    private val symmetricKeyAlias = "symalias"
    private val asymmetricKeyAlias = "asymalias"

    private lateinit var cipherWrapper: CipherWrapper
    private lateinit var keystoreRepository: KeystoreRepositoryImpl

    @Before
    fun init() {
        Log.i(CipherWrapper.TAG,"Running CipherWrapper on API ${Build.VERSION.SDK_INT}")
        cipherWrapper = CipherWrapper()
        keystoreRepository = KeystoreRepositoryImpl(InstrumentationRegistry.getInstrumentation().context, CipherWrapper())
        keystoreRepository.removeAllKeys()
    }

    @After
    fun removeKeys() {
        keystoreRepository.removeKey(symmetricKeyAlias)
        keystoreRepository.removeKey(asymmetricKeyAlias)
        keystoreRepository.removeAllKeys()
    }

    @Test
    fun testInvalidKeyRemove() {
        keystoreRepository.removeKey(invalidKeyAlias)
    }

    @Test
    fun testSymmetricKeyRetrieveSuccess() {
        val symmetricKeyGenerated = keystoreRepository.generateSymmetricKey(symmetricKeyAlias)
        val symmetricKeyRetrieved = keystoreRepository.getSymmetricKey(symmetricKeyAlias)
        assert(symmetricKeyGenerated == symmetricKeyRetrieved!!)
    }

    @Test
    fun testSymmetricKeyRetrieveError() {
        val symmetricKeyRetrieved = keystoreRepository.getSymmetricKey(invalidKeyAlias)
        assert(symmetricKeyRetrieved == null)
    }

    @Test
    fun testAsymmetricKeyRetrieveSuccess() {
        val asymmetricKeyGenerated = keystoreRepository.generateAsymmetricKey(asymmetricKeyAlias)
        val asymmetricKeyRetrieved = keystoreRepository.getAsymmetricKey(asymmetricKeyAlias)
        assert(asymmetricKeyGenerated == asymmetricKeyRetrieved!!)
    }

    @Test
    fun testAsymmetricKeyRetrieveError() {
        val symmetricKeyRetrieved = keystoreRepository.getAsymmetricKey(invalidKeyAlias)
        assert(symmetricKeyRetrieved == null)
    }

}