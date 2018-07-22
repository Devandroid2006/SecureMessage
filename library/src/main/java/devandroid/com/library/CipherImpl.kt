package devandroid.com.library

import android.util.Base64
import devandroid.com.library.internal.ICipher
import java.security.KeyPair
import javax.crypto.Cipher

/**
 * used for encryption and decryption
 */
class CipherImpl : ICipher {

    private lateinit var mCipher: Cipher;

    override fun initialize() {
        mCipher = Cipher.getInstance(ICipher.TRANSFORMATION)
    }

    override fun encrypt(keyPair: KeyPair?, message: String): String {
        mCipher.init(Cipher.ENCRYPT_MODE, keyPair?.public)
        return Base64.encodeToString(mCipher.doFinal(message.toByteArray()), Base64.DEFAULT);
    }

    override fun decrypt(keyPair: KeyPair?, cipher: String): String {
        val byteArray = Base64.decode(cipher, Base64.DEFAULT)
        mCipher.init(Cipher.DECRYPT_MODE, keyPair?.private)
        return String(mCipher.doFinal(byteArray));
    }
}