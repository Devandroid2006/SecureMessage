package devandroid.com.library

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class Utility {

    companion object {

        /**
         * used to get the shared preference object for the given fileName
         */
        fun getSharedPrefs(context: Context, fileName: String): SharedPreferences {
            return context.getSharedPreferences(fileName, MODE_PRIVATE);
        }
    }
}