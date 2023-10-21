package com.jlahougue.dndcharactersheet.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(value: T) {
            if (value == null) return
            if (value is Long && value == -1L) return
            observer.onChanged(value)
            removeObserver(this)
        }
    })
}

fun <T> LiveData<T>.observeNonNull(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(value: T) {
            if (value == null) return
            observer.onChanged(value)
        }
    })
}