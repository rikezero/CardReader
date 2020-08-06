package com.example.cardreader.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass


private val gsonConverter: GsonConverterFactory = GsonConverterFactory.create()

class RetroInit(url: String) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(gsonConverter)
        .build()

    private val retrofitDownload: Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(OkHttpClient.Builder().build())
        .build()

    fun <T : Any> create(clazz: KClass<T>): T = retrofit.create(clazz.java)

    fun <T : Any> startDownload(clazz: KClass<T>): T = retrofitDownload.create(clazz.java)

}

