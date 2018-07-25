package devandroid.com.library.internal

import android.util.Base64
import android.util.Log
import devandroid.com.library.ICipher
import java.security.KeyPair
import javax.crypto.Cipher

/**
 * used for encryption and decryption
 */
class CipherImpl : ICipher {

    private val TAG = "CipherImpl";

    private lateinit var mCipher: Cipher;

    override fun initialize() {
        mCipher = Cipher.getInstance(ICipher.TRANSFORMATION)
    }

    override fun encrypt(keyPair: KeyPair?, message: String): String {
        Log.d(TAG, "encrypt: [keyPair, message]");
        mCipher.init(Cipher.ENCRYPT_MODE, keyPair?.public)
        return Base64.encodeToString(mCipher.doFinal(message.toByteArray()), Base64.DEFAULT);
    }

    override fun decrypt(keyPair: KeyPair?, cipher: String): String {
        Log.d(TAG, "decrypt: [keyPair, cipher]");
        val byteArray = Base64.decode(cipher, Base64.DEFAULT)
        mCipher.init(Cipher.DECRYPT_MODE, keyPair?.private)
        return String(mCipher.doFinal(byteArray));
    }
}