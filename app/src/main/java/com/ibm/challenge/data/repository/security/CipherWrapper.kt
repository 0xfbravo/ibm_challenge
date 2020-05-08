package com.ibm.challenge.data.repository.security

import android.os.Build
import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.security.PrivateKey
import java.security.PublicKey
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

class CipherWrapper {

    companion object {
        const val RSA = "RSA"
        const val AES = "AES"

        val TRANSFORMATION_SYMMETRIC = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) { "AES/ECB/PKCS7Padding" } else { "AES/GCM/NoPadding" }
        const val TRANSFORMATION_ASYMMETRIC = "RSA/ECB/PKCS1Padding"

        const val TAG = "cipher_wrapper"
    }

    /**
     * Encrypts data using the key.
     *
     * @param data the data to encrypt
     * @param key the key to encryptAES data with
     */
    fun encryptAES(data: ByteArray, key: SecretKey): String {
        return Base64.encodeToString(encryptChunkWithAES(data, key), Base64.DEFAULT)
    }

    private fun encryptChunkWithAES(data: ByteArray, key: SecretKey): ByteArray {
        val useInitializationVector = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

        val cipher = Cipher.getInstance(TRANSFORMATION_SYMMETRIC)

        // If uses IV, inserts IV as prefix of encrypted data then generates a base64 string
        if (useInitializationVector) {
            cipher.init(Cipher.ENCRYPT_MODE, key, SecureRandom())
            val iv = cipher.iv
            val cipherData = cipher.doFinal(data)
            val byteBuffer = ByteBuffer.allocate(4 + iv.size + cipherData.size)
            byteBuffer.putInt(iv.size)
            byteBuffer.put(iv)
            byteBuffer.put(cipherData)
            val cipherMessage = byteBuffer.array()
            return cipherMessage
        }

        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedData = cipher.doFinal(data)
        return encryptedData
    }

    /**
     * Decrypts data using the key.
     *
     * @param data the data to decrypt
     * @param key the key to decrypt data with
     */
    fun decryptAES(data: String, key: SecretKey): ByteArray {
        val decoded = Base64.decode(data, Base64.DEFAULT)
        return decryptChunkWithAES(decoded, key)
    }

    private fun decryptChunkWithAES(data: ByteArray, key: SecretKey): ByteArray {
        val useInitializationVector = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        val cipher = Cipher.getInstance(TRANSFORMATION_SYMMETRIC)

        return if (useInitializationVector) {
            val byteBuffer = ByteBuffer.wrap(data)
            val ivLength = byteBuffer.int
            require(!(ivLength < 12 || ivLength >= 16)) {
                "Invalid IV length"
            }
            val iv = ByteArray(ivLength)
            byteBuffer.get(iv)
            val cipherData = ByteArray(byteBuffer.remaining())
            byteBuffer.get(cipherData)

            val gcmParameterSpec = GCMParameterSpec(128, iv)
            cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec)

            val decryptedData = cipher.doFinal(cipherData)
            decryptedData
        }
        else {
            cipher.init(Cipher.DECRYPT_MODE, key)
            val decryptedData = cipher.doFinal(data)
            decryptedData
        }
    }

    /**
     * Encrypts a secret key using the private key entry.
     *
     * @param key the secret key to encrypt
     * @param publicKey the public key to encrypt key with
     */
    fun encryptRSA(key: SecretKey, publicKey: PublicKey): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION_ASYMMETRIC)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)

        val outputStream = ByteArrayOutputStream()
        val cipherOutputStream = CipherOutputStream(outputStream, cipher)
        cipherOutputStream.write(key.encoded)
        cipherOutputStream.close()
        return outputStream.toByteArray()
    }

    /**
     * Decrypts a secret key (as byte array) using the private key entry.
     *
     * @param key the secret key to decrypt
     * @param privateKey the private key to decrypt key with
     */
    fun decryptRSA(key: ByteArray, privateKey: PrivateKey): SecretKey {
        val cipher = Cipher.getInstance(TRANSFORMATION_ASYMMETRIC)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)

        val cipherInputStream = CipherInputStream(ByteArrayInputStream(key), cipher)
        val values = arrayListOf<Byte>()

        var nextByte: Int
        do {
            nextByte = cipherInputStream.read()
            if (nextByte != -1) values.add(nextByte.toByte())
        } while (nextByte != -1)


        val decryptedKeyAsBytes = ByteArray(values.size)
        for (i in decryptedKeyAsBytes.indices) {
            decryptedKeyAsBytes[i] = values[i]
        }
        return SecretKeySpec(decryptedKeyAsBytes, AES)
    }

}
