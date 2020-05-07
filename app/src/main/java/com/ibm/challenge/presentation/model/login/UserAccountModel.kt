package com.ibm.challenge.presentation.model.login

data class UserAccountModel(val userID: Long? = null,
                            val name: String? = null,
                            val bankAccount: String? = null,
                            val agency: String? = null,
                            val balance: Double? = null)