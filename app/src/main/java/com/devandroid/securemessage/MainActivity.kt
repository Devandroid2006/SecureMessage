package com.devandroid.securemessage

import android.os.Bundle
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
        plainET.setText("Enter Text to be encrypt and decrypt.")
        //register click listeners
        resetBtn.setOnClickListener(this)
        encryptBtn.setOnClickListener(this)
        decryptBtn.setOnClickListener(this)

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
                mISecureMessage.putString("message", plainET.text.toString())
                Toast.makeText(this, "Saved successfully.", Toast.LENGTH_LONG).show()
            }
            R.id.decryptBtn -> {
                decryptedTV.setText(mISecureMessage.getString("message", "Null")
                )
            }
        }
    }
}
