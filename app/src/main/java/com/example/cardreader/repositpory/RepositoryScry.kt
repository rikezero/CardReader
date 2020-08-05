package com.example.cardreader.repositpory




import com.example.cardreader.model.DefaultCardsResponse
import com.example.cardreader.retrofit.RetroInit
import retrofit2.http.Url

class RepositoryScry {
    private var url = "https://api.scryfall.com/"
    private var service = ServiceScry::class

    private val serviceScry = RetroInit(url).create(service)

    suspend fun getDefaultBulk(): DefaultCardsResponse {
        return serviceScry.getDefaultBulk()
    }

    suspend fun getJSON(url: String){
        return serviceScry.getJSONFromURL(url)
    }



}