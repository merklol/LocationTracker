package com.bilingoal.locationtracker.utils
import com.google.android.material.textfield.TextInputLayout

val TextInputLayout.value: String get() = this.editText?.text.toString()