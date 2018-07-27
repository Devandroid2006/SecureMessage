package devandroid.com.library

import java.security.KeyPair

interface ICipher {

    companion object {
        /**
         * algorithm/blockMode/Padding format of the transformation
         *
         * used for encrypt and decrypt
         */
        const val TRANSFORMATION = "RSA/ECB/PKCS1Padding"
    }

    /**
     * initialize the cipher instance
     */
    fun initialize();

    /**
     * encrypt the given message, and return the cipher text
     *
     * for encryption public key will be used
     */
    fun encrypt(keyPair: KeyPair?, message: String): String

    /**
     * decrypt the given message and return the plain text
     *
     * for decryption private key will be used
     */
    fun decrypt(keyPair: KeyPair?, cipher: String): String
}