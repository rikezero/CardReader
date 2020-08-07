package com.example.cardreader.repositpory




import com.example.cardreader.model.DefaultCardsResponse
import com.example.cardreader.retrofit.RetroInit
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Url


class RepositoryScry {
    private var url = "https://api.scryfall.com/"
    private var service = ServiceScry::class
    private val serviceScry = RetroInit(url).create(service)
    private val scryDownload = RetroInit(url).startDownload(service)


    suspend fun getDefaultBulk(): DefaultCardsResponse {
        return serviceScry.getDefaultBulk()
    }

    suspend fun getJSON(url: String): Response<ResponseBody> {
        return scryDownload.getJSONFromURL(url)
    }



}