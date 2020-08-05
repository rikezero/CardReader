package com.example.cardreader.repositpory

import android.content.Context
import com.example.cardreader.data.DatabaseBuilder
import com.example.cardreader.data.dao.AccessCards


class RepositoryDatabase(context: Context) {
    private var database = DatabaseBuilder.getAppDatabase(context)
    private var accessPlay = database.accessCards()

    fun getAllCards(): AccessCards {
        return accessPlay
    }
    fun getCards(): AccessCards {
        return accessPlay
    }
}