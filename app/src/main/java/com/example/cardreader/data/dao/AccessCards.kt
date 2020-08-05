package com.example.cardreader.data.dao

import androidx.room.*
import com.example.cardreader.model.CardItem

@Dao
interface AccessCards {
    @Query("SELECT * FROM default_cards")
    fun all(): MutableList<CardItem>

    @Query("SELECT * FROM default_cards LIMIT 20 offset :count")
    fun getCards(count: Int = 0): MutableList<CardItem>

    @Query("SELECT COUNT(id) FROM default_cards")
    fun getCount():Int

    @Query("SELECT * FROM default_cards WHERE name LIKE :name")
    fun findByName(name: String): CardItem

    @Query("SELECT * FROM default_cards WHERE id LIKE :cardID")
    fun findById(cardID: String): CardItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg notes: CardItem)

    @Delete
    fun delete(cardItem: CardItem)

    @Update
    fun update(cardItem: CardItem)
}
