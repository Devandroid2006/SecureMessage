package com.devandroid.securemessage

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Toast
import devandroid.com.library.ISecureMessage
import devandroid.com.library.utils.SecureUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mISecureMessage: ISecureMessage;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initialize() {
        val sb = StringBuilder();
        //sample text to test
        for (i in 0..20) {
            sb.append("Enter sample Text to be encrypt and decrypt.")
        }
        plainET.setText(sb)

        //register click listeners
        resetBtn.setOnClickListener(this)
        encryptBtn.setOnClickListener(this)
        decryptBtn.setOnClickListener(this)

        //set scrolling feature for textview
        decryptedTV.movementMethod = ScrollingMovementMethod()
        //init secure instance
        mISecureMessage = SecureUtils.getSecurePreference(this, "sample.xml")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.resetBtn -> {
                plainET.setText("")
                decryptedTV.setText("")
            }
            R.id.encryptBtn -> {
                if (!plainET.text.isBlank()) {
                    mISecureMessage.putString("message", plainET.text.toString())
                    Toast.makeText(this, "Saved successfully.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Please Enter text.", Toast.LENGTH_LONG).show()
                }
            }
            R.id.decryptBtn -> {
                decryptedTV.setText(mISecureMessage.getString("message", "Null")
                )
            }
        }
    }
}
