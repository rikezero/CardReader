package com.example.cardreader.repositpory

import com.example.cardreader.model.AllCardsResponse
import com.example.cardreader.model.DefaultCardsResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ServiceScry {

    @GET("bulk-data/all-cards")
    suspend fun getAllBulk(): AllCardsResponse

    @GET("bulk-data/default-cards")
    suspend fun getDefaultBulk(): DefaultCardsResponse

    @Streaming
    @GET
    suspend fun getJSONFromURL(@Url url:String): Response<ResponseBody>

}
