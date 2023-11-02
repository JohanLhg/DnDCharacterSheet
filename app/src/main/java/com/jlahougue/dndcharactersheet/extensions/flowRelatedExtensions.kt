package com.jlahougue.dndcharactersheet.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> LifecycleOwner.collectLatestLifecycleFlow(flow: Flow<T>, action: (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(action)
        }
    }
}

fun <T> setCollectorIO(flow: Flow<T>, action: (T) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        flow.collect(action)
    }
}
