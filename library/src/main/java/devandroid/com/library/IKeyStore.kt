package devandroid.com.library

import android.content.Context
import java.security.KeyPair

interface IKeyStore {

    /**
     * declare constants of the keystore instance
     */
    companion object {
        /**
         * keystore provider, which will manage the keys
         *
         * AndroidKeyStore is Available form 18+
         */
        const val PROVIDER = "AndroidKeyStore"
        /**
         * RSA is the only algorithm available on pre Marshmallow
         */
        const val ALGORITHM = "RSA"

        const val MESSAGE_BLOCK = 240
    }

    /**
     * initialize the keystore instance with the context
     */
    fun initialize(context: Context)

    /**
     * generate a keypair with given key reference which is used for later use
     */
    fun generateKeyPair(alias: String): KeyPair?

    /**
     * get the keypair for the given alias
     */
    fun getKeyPair(alias: String): KeyPair?

    /**
     * check key is exists or not
     */
    fun isExists(alias: String): Boolean
}