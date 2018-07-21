package devandroid.com.library.internal

import android.content.Context
import java.security.KeyPair

interface IKeyStore {

    companion object {
        const val PROVIDER = "AndroidKeyStore";
        const val ALGORITHM = "RSA"
    }

    fun init(context: Context);

    fun generateKeyPair(alias: String): KeyPair?;

    fun getKeyPair(alias: String): KeyPair?;
}