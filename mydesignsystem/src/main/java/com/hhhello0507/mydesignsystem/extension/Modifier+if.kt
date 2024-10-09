package com.hhhello0507.mydesignsystem.extension

import androidx.compose.ui.Modifier

fun Modifier.`if`(condition: Boolean, transform: (Modifier) -> Modifier): Modifier {
    return if (condition) {
        transform(this)
    } else {
        this
    }
}