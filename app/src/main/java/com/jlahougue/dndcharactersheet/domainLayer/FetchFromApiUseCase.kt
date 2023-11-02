package com.jlahougue.dndcharactersheet.domainLayer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class FetchFromApiUseCase {
    private val _progressMax = MutableStateFlow(0)
    val progressMax = _progressMax.asStateFlow()

    private val _progress = MutableStateFlow(0)
    val progress = _progress.asStateFlow()

    private val _finished = MutableStateFlow(false)
    val finished = _finished.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            progress.collect {
                if (it == progressMax.value) {
                    _finished.emit(true)
                }
            }
        }
    }

    protected open fun invoke(onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            finished.collect {
                if (it) onFinished()
            }
        }
    }

    protected fun cancel() {
        CoroutineScope(Dispatchers.IO).launch {
            _progressMax.emit(1)
            _progress.emit(1)
        }
    }

    protected fun setProgressMax(max: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            _progressMax.emit(max)
        }
    }

    protected fun skip() {
        CoroutineScope(Dispatchers.IO).launch {
            _progress.emit(_progress.value + 1)
        }
    }

    protected fun progress() {
        CoroutineScope(Dispatchers.IO).launch {
            _progress.emit(_progress.value + 1)
        }
    }
}