package com.devandroid.securemessage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import devandroid.com.library.CipherImpl
import devandroid.com.library.KeyStoreImpl

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val keyStoreImpl = KeyStoreImpl();
        val cipherImpl = CipherImpl();

        cipherImpl.init();
        keyStoreImpl.init(this)

        val alias = "masterKey"

        keyStoreImpl.generateKeyPair(alias)

        val keyPair = keyStoreImpl.generateKeyPair(alias)

        val encrypt = cipherImpl.encrypt(keyPair, "Devaraja")

        val decrypt = cipherImpl.decrypt(keyPair, encrypt)

        println("Encrypt {$encrypt}")
        println("Decrypt {$decrypt}")
    }
}
