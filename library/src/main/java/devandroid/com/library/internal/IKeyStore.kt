package devandroid.com.library.internal

import android.content.Context
import java.security.KeyPair

interface IKeyStore {

    companion object {
        const val PROVIDER = "AndroidKeyStore"
        const val ALGORITHM = "RSA"
    }

    fun initialize(context: Context)

    fun generateKeyPair(alias: String): KeyPair?

    fun getKeyPair(alias: String): KeyPair?

    fun isExists(alias: String): Boolean
}