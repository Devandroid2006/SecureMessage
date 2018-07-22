package com.devandroid.securemessage

import android.os.Bundle
import devandroid.com.library.CipherImpl
import devandroid.com.library.KeyStoreImpl
import devandroid.com.library.SecureMessageImpl
import devandroid.com.library.Utility

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val iKeyStore = KeyStoreImpl();
        val iCipher = CipherImpl();
        val securePreferences = SecureMessageImpl()
        iKeyStore.initialize(this)
        iCipher.initialize()
        securePreferences.initialize(Utility.getSharedPrefs(this, "SamplePres"), iKeyStore, iCipher)

        securePreferences.putString("KEY", "Value");

        println(securePreferences.getString("KEY", "Default"))
    }
}
