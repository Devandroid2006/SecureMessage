package devandroid.com.library

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SecureUtils {

    companion object {

        /**
         * used to get the shared preference object for the given fileName
         */
        fun getSharedPrefs(context: Context, fileName: String): SharedPreferences {
            return context.getSharedPreferences(fileName, MODE_PRIVATE);
        }


        fun getSecurePreference(context: Context): SecureMessageImpl {
            val securePrefs = SecureMessageImpl();
//            val iKeyStore = KeyStoreImpl();
//            val iCipher = CipherImpl();
//            val sharedPreferences = getSharedPrefs()
//            securePrefs.initialize(sharedPreferences, iKeyStore, iCipher)
            return securePrefs;
        }
    }
}