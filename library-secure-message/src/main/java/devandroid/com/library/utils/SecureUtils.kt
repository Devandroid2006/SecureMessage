package devandroid.com.library.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import devandroid.com.library.ISecureMessage
import devandroid.com.library.internal.CipherImpl
import devandroid.com.library.internal.KeyStoreImpl
import devandroid.com.library.internal.SecureMessageImpl

class SecureUtils {

    companion object {

        /**
         * used to get the shared preference object for the given fileName
         */
        fun getSharedPrefs(context: Context, fileName: String): SharedPreferences {
            return context.getSharedPreferences(fileName, MODE_PRIVATE);
        }


        /**
         * return the secure shared prefs impl for the given file name
         */
        fun getSecurePreference(context: Context, preferencesFileName: String): ISecureMessage {
            val securePrefs = SecureMessageImpl();
            val iKeyStore = KeyStoreImpl();
            val iCipher = CipherImpl();
            val sharedPreferences = getSharedPrefs(context, preferencesFileName)
            //initialize the dependencies
            iKeyStore.initialize(context)
            iCipher.initialize()
            securePrefs.initialize(sharedPreferences, iKeyStore, iCipher)
            return securePrefs;
        }
    }
}