package com.example.dynamicfeature

import android.content.Context
import android.widget.FrameLayout

class CustomView(context: Context): FrameLayout(context) {

    init {
        inflate(context, R.layout.custom_view, this)
    }
}
