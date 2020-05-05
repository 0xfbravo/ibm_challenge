package com.ibm.challenge.data.core

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ibm.challenge.domain.core.DateUtils
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal object RequestManager {

    private const val CONNECT_TIMEOUT = 20L
    private const val READ_TIMEOUT = 20L
    private const val WRITE_TIMEOUT = 20L

    private fun provideRxDefaultErrorHandler() {
        RxJavaPlugins.setErrorHandler { it.printStackTrace() }
    }

    private fun provideHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val clientBuilder = OkHttpClient.Builder()

        /* Debug logger */
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(httpLoggingInterceptor)

        /* Client timeout */
        clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)

        return clientBuilder.build()
    }

    private fun getGSONInstance(isNullSerialized: Boolean): Gson {
        val builder = GsonBuilder()

        if (isNullSerialized)
            builder.serializeNulls()

        return builder
            .excludeFieldsWithoutExposeAnnotation()
            .setDateFormat(DateUtils.dataLayerFormat)
            .create()
    }

    fun provideRetrofit(baseURL: String, isNullSerialized: Boolean = true): Retrofit {
        provideRxDefaultErrorHandler()
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .client(provideHttpClient())
            .addConverterFactory(GsonConverterFactory.create(getGSONInstance(isNullSerialized)))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
}