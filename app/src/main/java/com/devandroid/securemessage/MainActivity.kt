package com.devandroid.securemessage

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initialize() {
        plainET.setText("Enter Text to be encrypt and decrypt.")
        //register click listeners
        resetBtn.setOnClickListener(this)
        encryptBtn.setOnClickListener(this)
        decryptBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.resetBtn -> {
                plainET.setText("")
                encryptedTV.setText("")
                decryptedTV.setText("")
            }
            R.id.encryptBtn -> {

            }
            R.id.decryptBtn -> {

            }
        }
    }
}
