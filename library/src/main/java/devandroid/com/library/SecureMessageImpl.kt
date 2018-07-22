package devandroid.com.library

import android.content.SharedPreferences
import devandroid.com.library.internal.ICipher
import devandroid.com.library.internal.IKeyStore
import devandroid.com.library.internal.ISecureMessage
import org.json.JSONArray

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
        return mSharedPrefs.getString(key, default)
    }

    override fun putInt(key: String, value: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getInt(key: String, default: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun putBoolean(key: String, value: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBoolean(key: String, default: Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun putDouble(key: String, value: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDouble(key: String, default: Double): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun putFloat(key: String, value: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFloat(key: String, default: Float): Float {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun putLong(key: String, value: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLong(key: String, default: Long): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun putEncryptedMessage(key: String, value: String) {
        mEditor.putString(key, prepareEncryptedJsonArray(value).toString());
        mEditor.commit()
    }

    override fun getDecryptedMessage(key: String): String {
        val keyPair = mIKeyStore.getKeyPair(mAliasKey)
        val encrypt = mSharedPrefs.getString(key, null)
        return prepareDecryptedMessage(mICipher.decrypt(keyPair, encrypt))
    }

    private fun prepareEncryptedJsonArray(value: String): JSONArray {
        val keyPair = mIKeyStore.getKeyPair(mAliasKey)
        val encrypt = mICipher.encrypt(keyPair, value)
        val jsonArray = JSONArray();
        return jsonArray;
    }

    private fun prepareDecryptedMessage(value: String): String {
        val jsonArray = JSONArray(value);
        return "";
    }
}