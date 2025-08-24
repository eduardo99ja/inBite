/*
 * Copyright (C) 2025 Eduardo Apodaca
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apodacatech.data.util

import android.annotation.SuppressLint
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec


class CryptoManager(
    private val alias: String = "user_prefs_aes_key",
    private val ivSizeBytes: Int = 12,
    private val tagLengthBits: Int = 128
) {
    private val androidKeyStore = "AndroidKeyStore"
    private val transformation = "AES/GCM/NoPadding"


    @SuppressLint("NewApi")
    private fun getOrCreateSecretKey(): SecretKey {
        val ks = KeyStore.getInstance(androidKeyStore).apply { load(null) }
        (ks.getEntry(alias, null) as? KeyStore.SecretKeyEntry)?.secretKey?.let { return it }

        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, androidKeyStore)
        val spec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .setRandomizedEncryptionRequired(true)
            .setUnlockedDeviceRequired(true)
            .build()
        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }


    fun encrypt(plainText: String): String {
        val key = getOrCreateSecretKey()
        val cipher = Cipher.getInstance(transformation)

        // ENCRYPT_MODE → sin IV, Android genera uno
        cipher.init(Cipher.ENCRYPT_MODE, key)

        val iv = cipher.iv // este IV es autogenerado
        val cipherBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

        // Guardamos IV + CIPHERTEXT en Base64
        val combined = iv + cipherBytes
        return Base64.encodeToString(combined, Base64.NO_WRAP)
    }


    fun decrypt(base64Combined: String): String {
        val key = getOrCreateSecretKey()
        val combined = Base64.decode(base64Combined, Base64.NO_WRAP)

        val iv = combined.copyOfRange(0, ivSizeBytes)
        val cipherBytes = combined.copyOfRange(ivSizeBytes, combined.size)

        val cipher = Cipher.getInstance(transformation)
        val gcmSpec = GCMParameterSpec(tagLengthBits, iv)
        cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec)

        val plain = cipher.doFinal(cipherBytes)
        return plain.toString(Charsets.UTF_8)
    }

}