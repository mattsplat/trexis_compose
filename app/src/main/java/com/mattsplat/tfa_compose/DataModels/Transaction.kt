package com.mattsplat.tfa_compose.DataModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaction(
    var id: String,
    var title: String,
    var balance: String,
) : Parcelable
