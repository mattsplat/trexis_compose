package com.mattsplat.tfa_compose.DataModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthCredentials(
    var username: String,
    var password: String
) : Parcelable {
    val toBody
        get() = "username=$username&password=$password"

}