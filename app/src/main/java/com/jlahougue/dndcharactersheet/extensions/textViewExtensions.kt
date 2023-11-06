package com.jlahougue.dndcharactersheet.extensions

import android.text.Editable
import android.widget.TextView
import androidx.core.widget.addTextChangedListener

fun TextView.addFocusedTextChangedListener (afterTextChanged: (Editable?) -> Unit) {
    this.addTextChangedListener { text ->
        if (this.isFocused) afterTextChanged(text)
    }
}