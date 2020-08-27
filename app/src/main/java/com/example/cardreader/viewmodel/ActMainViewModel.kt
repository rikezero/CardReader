package com.example.cardreader.viewmodel


import android.app.Application
import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cardreader.R
import com.example.cardreader.custom.saveFile
import com.example.cardreader.custom.updateDatabase
import com.example.cardreader.model.CardItem
import com.example.cardreader.model.DefaultCardsResponse
import com.example.cardreader.repositpory.RepositoryDatabase
import com.example.cardreader.repositpory.RepositoryScry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.Url


class ActMainViewModel(application: Application) : AndroidViewModel(application) {

    val dataSet = MutableLiveData<MutableList<CardItem>>()
    val bulk = MutableLiveData<DefaultCardsResponse>()
    private val context = getApplication<Application>().applicationContext
    private var repository = RepositoryDatabase(context)
    private var repositoryScry = RepositoryScry()


    fun getAllCards() {
        dataSet.postValue(repository.getAllCards().all())
    }
    fun getCards() {
        dataSet.postValue(repository.getAllCards().getCards())

    }

    fun api() = CoroutineScope(Dispatchers.IO).launch {
        val response = repositoryScry.getDefaultBulk()
        bulk.postValue(response)
        println(response.downloadUri)
        val responseBody=repositoryScry.
        getJSON(response.downloadUri)
            .body()
        val fileName = response.name + context.getString(R.string.json_extension)
        val saveLocation = context.filesDir.absolutePath + "/" + fileName
       saveFile(responseBody, saveLocation)
        context.updateDatabase(fileName)
    }



}