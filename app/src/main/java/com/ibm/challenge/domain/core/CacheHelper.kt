package com.ibm.challenge.domain.core

enum class CacheHelper(val key: String,
                       val cacheDurationMillis: Long? = null) {

    UserAccount("user_account")

}