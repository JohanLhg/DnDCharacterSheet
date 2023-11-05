package com.jlahougue.dndcharactersheet.extensions

import java.text.DecimalFormat

fun Int.feetToMeterString(): String {
    val roundedFormatter = DecimalFormat("#.5")
    val decimalFormatter = DecimalFormat("#.#")
    var meter = this * 0.3048
    meter = roundedFormatter.format(meter).toDouble()
    return decimalFormatter.format(meter)
}