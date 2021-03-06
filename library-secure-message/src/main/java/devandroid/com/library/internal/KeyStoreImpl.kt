package devandroid.com.library.internal

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import devandroid.com.library.IKeyStore
import java.math.BigInteger
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.util.*
import javax.security.auth.x500.X500Principal

/**
 * used for keypair management
 *
 * android api below 23 supports only RSA Algorithm with androidKeystore
 */
class KeyStoreImpl : IKeyStore {

    private val TAG = "KeyStoreImpl";

    private lateinit var mKeyStore: KeyStore;

    private lateinit var mContext: Context;

    override fun initialize(context: Context) {
        mKeyStore = KeyStore.getInstance(IKeyStore.PROVIDER)
        mKeyStore.load(null)
        mContext = context
    }

    override fun isExists(alias: String): Boolean {
        Log.d(TAG, "isExists: [$alias]");
        return mKeyStore.isKeyEntry(alias)
    }

    override fun generateKeyPair(alias: String): KeyPair? {
        Log.d(TAG, "generateKeyPair: [$alias]");
        val keyPairGenerator = KeyPairGenerator.getInstance(IKeyStore.ALGORITHM, IKeyStore.PROVIDER)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initForMarshaMallow(keyPairGenerator, alias)
        } else {
            initForKitKat(keyPairGenerator, alias)
        }
        val keyPair = keyPairGenerator.generateKeyPair()
        return keyPair;
    }

    override fun getKeyPair(alias: String): KeyPair? {
        Log.d(TAG, "getKeyPair: [$alias]");
        return KeyPair(mKeyStore.getCertificate(alias).publicKey, mKeyStore.getKey(alias, null) as PrivateKey)
    }


    private fun initForKitKat(keyPairGenerator: KeyPairGenerator, alias: String) {
        Log.d(TAG, "initForKitKat: [$keyPairGenerator, $alias]");
        val startTime = Calendar.getInstance().time;
        val endTime = Calendar.getInstance();
        endTime.add(Calendar.YEAR, 20);

        val keyPairGeneratorSpec = KeyPairGeneratorSpec.Builder(mContext)
                .setStartDate(startTime)
                .setEndDate(endTime.time)
                .setAlias(alias)
                .setSubject(X500Principal("CN=Common Name, O=Organization, C=Country"))
                .setSerialNumber(BigInteger.TEN)
                .build();
        keyPairGenerator.initialize(keyPairGeneratorSpec)

    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun initForMarshaMallow(keyPairGenerator: KeyPairGenerator, alias: String) {
        Log.d(TAG, "initForMarshaMallow: [$keyPairGenerator, $alias]");
        val startTime = Calendar.getInstance().time;
        val endTime = Calendar.getInstance();
        endTime.add(Calendar.YEAR, 20);

        val keyGenParameterSpec = KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setKeyValidityStart(startTime)
                .setKeyValidityEnd(endTime.time)
                .setCertificateSubject(X500Principal("CN=Common Name, O=Organization, C=Country"))
                .setCertificateSerialNumber(BigInteger.TEN)
                .build()
        keyPairGenerator.initialize(keyGenParameterSpec)
    }
}