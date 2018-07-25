package devandroid.com.library.internal

import android.content.SharedPreferences
import android.util.Log
import devandroid.com.library.ICipher
import devandroid.com.library.IKeyStore
import devandroid.com.library.IKeyStore.Companion.MESSAGE_BLOCK
import devandroid.com.library.ISecureMessage
import org.json.JSONArray

class SecureMessageImpl : ISecureMessage {

    private val TAG = "SecureMessageImpl";

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
        Log.d(TAG, "putString: [key, value]");
        putEncryptedMessage(key, value)
    }

    override fun getString(key: String, default: String): String? {
        Log.d(TAG, "getString: [key, default]");
        val decryptedMessage = getDecryptedMessage(key)
        return if (null == decryptedMessage) default else decryptedMessage
    }

    override fun putInt(key: String, value: Int) {
        Log.d(TAG, "putInt() called");
        putEncryptedMessage(key, value.toString())
    }

    override fun getInt(key: String, default: Int): Int {
        Log.d(TAG, "getInt: [key, default]");
        val decryptedMessage = getDecryptedMessage(key)
        return if (null == decryptedMessage) default else decryptedMessage.toInt()
    }

    override fun putBoolean(key: String, value: Boolean) {
        Log.d(TAG, "putBoolean: [key, value]");
        putEncryptedMessage(key, value.toString())
    }

    override fun getBoolean(key: String, default: Boolean): Boolean {
        Log.d(TAG, "getBoolean: [key, default]");
        val decryptedMessage = getDecryptedMessage(key)
        return if (null == decryptedMessage) default else decryptedMessage.toBoolean()
    }

    override fun putDouble(key: String, value: Double) {
        Log.d(TAG, "putDouble: [key, value]");
        putEncryptedMessage(key, value.toString())
    }

    override fun getDouble(key: String, default: Double): Double {
        Log.d(TAG, "getDouble: [key, default]");
        val decryptedMessage = getDecryptedMessage(key)
        return if (null == decryptedMessage) default else decryptedMessage.toDouble()
    }

    override fun putFloat(key: String, value: Float) {
        Log.d(TAG, "putFloat: [key, value]");
        putEncryptedMessage(key, value.toString())
    }

    override fun getFloat(key: String, default: Float): Float {
        Log.d(TAG, "getFloat: [key, default]");
        val decryptedMessage = getDecryptedMessage(key)
        return if (null == decryptedMessage) default else decryptedMessage.toFloat()
    }

    override fun putLong(key: String, value: Long) {
        putEncryptedMessage(key, value.toString())
    }

    override fun getLong(key: String, default: Long): Long {
        Log.d(TAG, "getLong: [key, default]");
        val decryptedMessage = getDecryptedMessage(key)
        return if (null == decryptedMessage) default else decryptedMessage.toLong()
    }

    private fun putEncryptedMessage(key: String, value: String) {
        Log.d(TAG, "putEncryptedMessage: [key, value]");
        mEditor.putString(key, prepareEncryptedJsonArray(value).toString());
        mEditor.commit()
    }

    private fun getDecryptedMessage(key: String): String {
        Log.d(TAG, "getDecryptedMessage: [key]");
        val encrypt = JSONArray(mSharedPrefs.getString(key, null))
        return prepareDecryptedMessage(encrypt)
    }

    private fun prepareEncryptedJsonArray(value: String): JSONArray {
        Log.d(TAG, "prepareEncryptedJsonArray: [value]");
        val keyPair = mIKeyStore.getKeyPair(mAliasKey)
        val chunked = value.chunked(MESSAGE_BLOCK)
        val jsonArray = JSONArray();
        for (str in chunked) {
            println("Chunk :$str")
            val encrypt = mICipher.encrypt(keyPair, str)
            jsonArray.put(encrypt)
        }
        return jsonArray;
    }

    private fun prepareDecryptedMessage(value: JSONArray): String {
        Log.d(TAG, "prepareDecryptedMessage: [value]");
        val keyPair = mIKeyStore.getKeyPair(mAliasKey)
        val sb = StringBuilder()
        for (str in 0..(value.length() - 1)) {
            sb.append(mICipher.decrypt(keyPair, value.getString(str)))
        }
        return sb.toString();
    }
}