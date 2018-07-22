package devandroid.com.library.internal

import android.content.SharedPreferences

/**
 * responsible for encrypt/decrypt keys and values
 */
interface ISecureMessage {

    fun initialize(sharedPreferences: SharedPreferences, iKeyStore: IKeyStore, iCipher: ICipher)

    fun putString(key: String, value: String)

    fun getString(key: String, default: String): String?

    fun putInt(key: String, value: Int)

    fun getInt(key: String, default: Int): Int

    fun putBoolean(key: String, value: Boolean)

    fun getBoolean(key: String, default: Boolean): Boolean

    fun putDouble(key: String, value: Double)

    fun getDouble(key: String, default: Double): Double

    fun putFloat(key: String, value: Float)

    fun getFloat(key: String, default: Float): Float

    fun putLong(key: String, value: Long)

    fun getLong(key: String, default: Long): Long

    fun putEncryptedMessage(key: String, value: String)

    fun getDecryptedMessage(key: String): String
}