package com.example.cardreader.repositpory


import android.net.Uri
import com.example.cardreader.model.AllCardsResponse
import com.example.cardreader.model.DefaultCardsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ServiceScry {

    @GET("bulk-data/all-cards")
    suspend fun getAllBulk(): AllCardsResponse

    @GET("bulk-data/default-cards")
    suspend fun getDefaultBulk(): DefaultCardsResponse

    @GET
    suspend fun getJSONFromURL(@Url url: String)

//    @GET("?method=geo.gettoptracks")
//    suspend fun getGeoTopTrack(
//            @Query("api_key")
//            apikey: String,
//            @Query("page")
//            page: Int = 1,
//            @Query("country")
//            country: String,
//            @Query("format")
//            format: String
//    ): GeoTopTracksResponse
	
}
