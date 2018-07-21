package devandroid.com.library.internal

import java.security.KeyPair

interface ICipher {

    companion object {
        const val TRANSFORMATION = "RSA/ECB/PKCS1Padding"
    }

    fun init();

    fun encrypt(keyPair: KeyPair?, message: String): String

    fun decrypt(keyPair: KeyPair?, cipher: String): String
}