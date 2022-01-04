package com.mattsplat.tfa_compose.DataModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Account(
    var id: String,
    var name: String,
    var balance: String,
) : Parcelable
