package com.example.cardreader.model



import com.google.gson.annotations.SerializedName

data class Card(
    @SerializedName("card_list")
    val artist: MutableSet<CardItem>
)

