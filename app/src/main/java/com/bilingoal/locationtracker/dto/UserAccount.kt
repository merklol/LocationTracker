package com.bilingoal.locationtracker.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserAccount(
    var email: String? = "",
    var name: String? = "",
    var password: String? = "",
    var uid: String? = ""
) : Parcelable