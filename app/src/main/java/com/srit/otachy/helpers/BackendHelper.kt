package com.srit.otachy.helpers

import android.util.Base64
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets

object BackendHelper {
    const val IMAGE_URL = "https://23.238.35.18:5400/api/"
    const val API_BASE_URL = "http://23.238.35.18:5400/"
    val gson = GsonBuilder()// TODO: set date format
        .setLenient()
        .create()







    private val mBuilder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(gson)
        )

    // TODO: deal with timeout
    private val mHttpClient = OkHttpClient.Builder()
    private val mHttpClientWithInterceptor = OkHttpClient.Builder()


    val retrofit: Retrofit
        get() = mBuilder
            .client(NetworkHandler.getUnsafeOkHttpClient())
            .build()


    //val retrofit=NetworkHandler.getRetrofit(API_BASE_URL)

    val retrofitWithAuth: Retrofit
        get() {
            if (mHttpClientWithInterceptor.interceptors().size == 0) {
                mHttpClientWithInterceptor.addInterceptor(
                    Interceptor { chain ->
                        val token =
                            SharedPrefHelper.getInstance().accessToken ?: return@Interceptor null
                        val newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", "bearer "+token)
                            .build()
                        chain.proceed(newRequest)
                    }
                )
            }
            return mBuilder
                .client(mHttpClientWithInterceptor.build())
                .build()
        }
}