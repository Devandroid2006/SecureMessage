package com.devandroid.securemessage;

import android.util.Log;

import io.reactivex.observers.DisposableObserver;

public class AppDisposable<T> extends DisposableObserver<T> {

    private static final String TAG = "AppDisposable";

    @Override
    public void onNext(T value) {
        Log.d(TAG, "onNext() called with: value = [" + value + "]");
    }

    @Override
    public void onError(Throwable exception) {
        Log.d(TAG, "onError() called with: exception = [" + exception + "]");
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete() called");
    }
}
