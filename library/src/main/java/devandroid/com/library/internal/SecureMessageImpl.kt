package devandroid.com.library.internal

import android.content.SharedPreferences
import devandroid.com.library.ICipher
import devandroid.com.library.IKeyStore
import devandroid.com.library.IKeyStore.Companion.MESSAGE_BLOCK
import devandroid.com.library.ISecureMessage
import org.json.JSONArray
import timber.log.Timber

class SecureMessageImpl : ISecureMessage {

    private lateinit var mSharedPrefs: SharedPreferences;

    private lateinit var mEditor: SharedPreferences.Editor

    private lateinit var mIKeyStore: IKeyStore;

    private lateinit var mICipher: ICipher;

    private val mAliasKey = "prefs_master_key"

    override fun initialize(sharedPreferences: SharedPreferences, iKeyStore: IKeyStore, iCipher: ICipher) {
        mICipher = iCipher
        mIKeyStore = iKeyStore
        mSharedPrefs = sharedPreferences;
        mEditor = sharedPreferences.edit()
        //generate key not exists
        if (!mIKeyStore.isExists(mAliasKey)) {
            mIKeyStore.generateKeyPair(mAliasKey)
        }
    }

    override fun putString(key: String, value: String) {
        putEncryptedMessage(key, value)
    }

    override fun getString(key: String, default: String): String? {
        val decryptedMessage = getDecryptedMessage(key)
        return if (null == decryptedMessage) default else decryptedMessage
    }

    override fun putInt(key: String, value: Int) {
        putEncryptedMessage(key, value.toString())
    }

    override fun getInt(key: String, default: Int): Int {
        val decryptedMessage = getDecryptedMessage(key)
        return if (null == decryptedMessage) default else decryptedMessage.toInt()
    }

    override fun putBoolean(key: String, value: Boolean) {
        putEncryptedMessage(key, value.toString())
    }

    override fun getBoolean(key: String, default: Boolean): Boolean {
        val decryptedMessage = getDecryptedMessage(key)
        return if (null == decryptedMessage) default else decryptedMessage.toBoolean()
    }

    override fun putDouble(key: String, value: Double) {
        putEncryptedMessage(key, value.toString())
    }

    override fun getDouble(key: String, default: Double): Double {
        val decryptedMessage = getDecryptedMessage(key)
        return if (null == decryptedMessage) default else decryptedMessage.toDouble()
    }

    override fun putFloat(key: String, value: Float) {
        putEncryptedMessage(key, value.toString())
    }

    override fun getFloat(key: String, default: Float): Float {
        val decryptedMessage = getDecryptedMessage(key)
        return if (null == decryptedMessage) default else decryptedMessage.toFloat()
    }

    override fun putLong(key: String, value: Long) {
        putEncryptedMessage(key, value.toString())
    }

    override fun getLong(key: String, default: Long): Long {
        val decryptedMessage = getDecryptedMessage(key)
        return if (null == decryptedMessage) default else decryptedMessage.toLong()
    }

    private fun putEncryptedMessage(key: String, value: String) {
        Timber.d("Message to be encrypt :$value, length :${value.length}")
        mEditor.putString(key, prepareEncryptedJsonArray(value).toString());
        mEditor.commit()
    }

    private fun getDecryptedMessage(key: String): String {
        val encrypt = JSONArray(mSharedPrefs.getString(key, null))
        Timber.d("Message to be decrypt :$encrypt, length :${encrypt.length()}")
        return prepareDecryptedMessage(encrypt)
    }

    private fun prepareEncryptedJsonArray(value: String): JSONArray {
        val keyPair = mIKeyStore.getKeyPair(mAliasKey)
        val chunked = value.chunked(MESSAGE_BLOCK)
        val jsonArray = JSONArray();
        for (str in chunked) {
            println("Chunk :$str")
            val encrypt = mICipher.encrypt(keyPair, value)
            jsonArray.put(encrypt)
            Timber.d("Encrypted :{$encrypt}")
        }
        return jsonArray;
    }

    private fun prepareDecryptedMessage(value: JSONArray): String {
        val keyPair = mIKeyStore.getKeyPair(mAliasKey)
        val sb = StringBuilder()
        for (str in 0..(value.length() - 1)) {
            sb.append(mICipher.decrypt(keyPair, value.getString(str)))
        }
        Timber.d("Decrypt :{${sb}}")
        return sb.toString();
    }
}