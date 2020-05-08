package com.ibm.challenge.domain.repository.remote

import android.util.Log

interface RemoteRepository {

    fun onRequestSuccess(interactor: String, response: String) {
        Log.d(interactor, "Request Success: $response")
    }

    fun onRequestError(interactor: String, response: String) {
        Log.e(interactor, "Request Error: $response")
    }

}