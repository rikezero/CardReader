package com.example.cardreader.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class AllCardsResponse(
    @SerializedName("object")
    val objectX: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("uri")
    val uri: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("compressed_size")
    val compressedSize: Int,
    @SerializedName("download_uri")
    val downloadUri: String,
    @SerializedName("content_type")
    val contentType: String,
    @SerializedName("content_encoding")
    val contentEncoding: String
) : Parcelable