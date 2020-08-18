package com.example.cardreader.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "default_cards")
data class CardItem(
    @ColumnInfo(name = "artist") val artist: String,
    @ColumnInfo(name = "artist_ids") val artist_ids: String,
    @ColumnInfo(name = "card_back_id") val card_back_id: String,
    @ColumnInfo(name = "cmc") val cmc: String,
    @ColumnInfo(name = "color_identity") val color_identity: String,
    @ColumnInfo(name = "colors") val colors: String,
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "illustration_id") val illustration_id: String,
    @ColumnInfo(name = "image_uris") val image_uris: String,
    @ColumnInfo(name = "keywords") val keywords: String,
    @ColumnInfo(name = "lang") val lang: String,
    @ColumnInfo(name = "legalities") val legalities: String,
    @ColumnInfo(name = "mana_cost") val mana_cost: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "object") val objekt: String,
    @ColumnInfo(name = "oracle_text") val oracle_text: String,
    @ColumnInfo(name = "power") val power: String,
    @ColumnInfo(name = "produced_mana") val produced_mana: String,
    @ColumnInfo(name = "rarity") val rarity: String,
    @ColumnInfo(name = "rulings_uri") val rulings_uri: String,
    @ColumnInfo(name = "scryfall_set_uri") val scryfall_set_uri: String,
    @ColumnInfo(name = "scryfall_uri") val scryfall_uri: String,
    @ColumnInfo(name = "set") val set: String,
    @ColumnInfo(name = "set_name") val set_name: String,
    @ColumnInfo(name = "set_search_uri") val set_search_uri: String,
    @ColumnInfo(name = "set_uri") val set_uri: String,
    @ColumnInfo(name = "toughness") val toughness: String,
    @ColumnInfo(name = "type_line") val type_line: String,
    @ColumnInfo(name = "uri") val uri: String
)