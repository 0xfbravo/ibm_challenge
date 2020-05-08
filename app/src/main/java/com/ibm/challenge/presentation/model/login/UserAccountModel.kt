package com.ibm.challenge.presentation.model.login

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserAccountModel(val userID: Long? = null,
                            val name: String? = null,
                            val bankAccount: String? = null,
                            val agency: String? = null,
                            val balance: Double? = null) : Parcelable {

    companion object {
        const val bundleKey = "USER_ACCOUNT_MODEL"
    }

}