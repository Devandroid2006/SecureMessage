package devandroid.com.library.internal

import android.content.SharedPreferences

/**
 * responsible for encrypt/decrypt keys and values
 */
interface ISecureMessage {

    /**
     * initialize the secure message with shared preferences which is used to saving the encrypted values
     * in string format, Ikeystore is required to manage keys, icipher is used for encrypt and decrypt.
     */
    fun initialize(sharedPreferences: SharedPreferences, iKeyStore: IKeyStore, iCipher: ICipher)

    /**
     * key:String reference for the value
     */
    fun putString(key: String, value: String)

    /**
     * key:String reference for the value
     *  return decrypted value or else default value if key doesn't exist
     */
    fun getString(key: String, default: String): String?

    /**
     * key:String reference for the value
     */
    fun putInt(key: String, value: Int)

    /**
     * key:String reference for the value
     *  return decrypted value or else default value if key doesn't exist
     */
    fun getInt(key: String, default: Int): Int

    /**
     * key:String reference for the value
     * return decrypted boolean value
     */
    fun putBoolean(key: String, value: Boolean)

    /**
     * key:String reference for the value
     *  return decrypted value or else default value if key doesn't exist
     */
    fun getBoolean(key: String, default: Boolean): Boolean

    /**
     * key:String reference for the value
     */
    fun putDouble(key: String, value: Double)

    /**
     * key:String reference for the value
     *  return decrypted value or else default value if key doesn't exist
     */
    fun getDouble(key: String, default: Double): Double

    /**
     * key:String reference for the value
     */
    fun putFloat(key: String, value: Float)

    /**
     * key:String reference for the value
     *  return decrypted value or else default value if key doesn't exist
     */
    fun getFloat(key: String, default: Float): Float

    /**
     * key:String reference for the value
     * save the long value in encrypted format
     */
    fun putLong(key: String, value: Long)

    /**
     * key:String reference for the value
     *  return decrypted value or else default value if key doesn't exist
     */
    fun getLong(key: String, default: Long): Long

    /**
     * generic message encrypt
     * key:String reference for the value
     * value to be saved as encrypted
     */
    fun putEncryptedMessage(key: String, value: String)

    /**
     * generic message decrypt
     * key:String reference for the value
     *  return null value if key doesn't exist
     */
    fun getDecryptedMessage(key: String): String
}