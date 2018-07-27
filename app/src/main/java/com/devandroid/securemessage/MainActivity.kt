package com.devandroid.securemessage

import android.app.ProgressDialog
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import devandroid.com.library.ISecureMessage
import devandroid.com.library.utils.SecureUtils
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mISecureMessage: ISecureMessage;

    lateinit var progressDialog: ProgressDialog;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()

        Observable.create(ObservableOnSubscribe<String> {
            //sample test
            mISecureMessage.putBoolean("boolean", true)

            mISecureMessage.putBoolean("boolean", false)

            Timber.d("Message Boolean:${mISecureMessage.getBoolean("boolean", false)}")

            mISecureMessage.putLong("Long", 1000)

            mISecureMessage.putLong("Long", 200)

            Timber.d("Message Long: ${mISecureMessage.getLong("Long", 2)}")

            it.onNext("DONE.")
            it.onComplete()
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { progressDialog.show() }
                .doOnTerminate { progressDialog.dismiss() }
                .subscribe(object : AppDisposable<String>() {
                })

    }

    private fun initialize() {
        val sb = StringBuilder();
        //sample text to test
        for (i in 0..2) {
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

        progressDialog = ProgressDialog(this);
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.resetBtn -> {
                plainET.setText("")
                decryptedTV.setText("")
            }
            R.id.encryptBtn -> {
                if (!plainET.text.isBlank()) {
                    Observable.create(ObservableOnSubscribe<String> {
                        mISecureMessage.putString("message", plainET.text.toString())
                        it.onNext("DONE.")
                        it.onComplete()
                    }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe { progressDialog.show() }
                            .doOnTerminate { progressDialog.dismiss() }
                            .subscribe(object : AppDisposable<String>() {

                                override fun onNext(t: String) {
                                    mISecureMessage.putString("message", plainET.text.toString())
                                    Toast.makeText(baseContext, "Saved successfully.", Toast.LENGTH_LONG).show()
                                }

                            })
                } else {
                    Toast.makeText(this, "Please Enter text.", Toast.LENGTH_LONG).show()
                }
            }
            R.id.decryptBtn -> {
                Observable.create(ObservableOnSubscribe<String> {
                    it.onNext(mISecureMessage.getString("message", "Null")!!)
                    it.onComplete()
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { progressDialog.show() }
                        .doOnTerminate { progressDialog.dismiss() }
                        .subscribe(object : AppDisposable<String>() {
                            override fun onNext(t: String) {
                                Log.d("", "onNext: [t]");
                                decryptedTV.setText(t)
                            }

                        })
            }
        }
    }
}
