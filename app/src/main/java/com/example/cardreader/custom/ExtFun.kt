package com.example.cardreader.custom

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.JsonReader
import android.util.JsonToken
import android.util.Log
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.example.cardreader.base.ContextFinder
import com.example.cardreader.model.CardItem
import com.google.gson.Gson
import okhttp3.ResponseBody
import java.io.*
import kotlin.reflect.KClass


val Context.inflater get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

@Suppress("UNCHECKED_CAST")
inline fun <reified Binding : ViewBinding> ContextFinder.viewBind() = lazy {
    Binding::class.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, cont.inflater) as Binding
}

@Suppress("UNCHECKED_CAST")
fun <Binding : ViewBinding> Context.viewBind(klass: KClass<Binding>) =
    klass.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, inflater) as Binding

val Context.activity: Activity
    get() = when (this) {
        is Activity -> this
        else -> (this as ContextWrapper).baseContext.activity
    }

fun Context.writeFile(fileName: String, body: ResponseBody?) {
    var input: InputStream? = null
    val outputStream: FileOutputStream
    if (body != null) {
        try {
            input = body.byteStream()
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }

        } catch (e: Exception) {
            Log.e("saveFileError", e.toString())
        } catch (e: FileNotFoundException) {
            Log.e("FileNotFound", e.toString())
        }
    } else {
        return
    }
}


fun saveFile(body: ResponseBody?, pathToSave: String): String {
    if (body == null)
        return ""
    var input: InputStream? = null
    try {
        input = body.byteStream()
        //val file = File(getCacheDir(), "cacheFileAppeal.srl")
        val fos = FileOutputStream(pathToSave)
        fos.use { output ->
            val buffer = ByteArray(4 * 1024) // or other buffer size
            var read: Int
            while (input.read(buffer).also { read = it } != -1) {
                output.write(buffer, 0, read)
            }
            output.flush()
        }
        return pathToSave
    } catch (e: Exception) {
        Log.e("saveFile", e.toString())
    } finally {
        input?.close()
    }
    return ""
}

fun Context.updateDatabase(fileName: String) {
    val file = File(filesDir, fileName)
    if (file.exists()) {
        val cardList = mutableListOf<CardItem>()
        val inputStream = FileInputStream(file)
        val reader = InputStreamReader(inputStream)
        val jReader = JsonReader(reader)
        jReader.beginArray()
        while (jReader.hasNext()) {
            cardList.add(readCard(jReader))
        }
        jReader.endArray()

    }
}

fun readCard(reader: JsonReader) {
    var artist: String
    var artist_ids: String
    var card_back_id: String
    var cmc: String
    var color_identity: String
    var colors: String
    var id: String
    var illustration_id: String
    var image_uris: String
    var keywords: String
    var lang: String
    var legalities: String
    var mana_cost: String
    var name: String
    var objekt: String
    var oracle_text: String
    var power: String
    var preview: String
    var produced_mana: String
    var rarity: String
    var rulings_uri: String
    var scryfall_set_uri: String
    var scryfall_uri: String
    var set: String
    var set_name: String
    var set_search_uri: String
    var set_uri: String
    var toughness: String
    var type_line: String
    var uri: String

    reader.beginObject()
    while (reader.hasNext()) {
        val card: String = reader.nextName()
        if (card.equals("object")) {
            objekt = reader.nextString()
        } else if (card.equals("id")) {
            id = reader.nextString()
        } else if (card.equals("name")) {
            name = reader.nextString()
        } else if (card.equals("lang")) {
            lang = reader.nextString()
        } else if (card.equals("uri")) {
            uri = reader.nextString()
        } else if (card.equals("image_uris") && reader.peek() != JsonToken.NULL) {
            image_uris = readImageArray(reader)
        } else if (card.equals("uri")) {
            uri = reader.nextString()
        }
    }
    reader.endObject()
}

fun readImageArray(reader: JsonReader): String {
    val list: ArrayList<String> = ArrayList()

    reader.beginObject()
    while (reader.hasNext()) {
        val field: String = reader.nextName()
        when {
            field.equals("small") -> {
                list.add(0, reader.nextString())
            }
            field.equals("normal") -> {
                list.add(1, reader.nextString())
            }
            field.equals("large") -> {
                list.add(2, reader.nextString())
            }
            field.equals("png") -> {
                list.add(3, reader.nextString())
            }
            field.equals("art_crop") -> {
                list.add(4, reader.nextString())
            }
            else -> {
                list.add(5, reader.nextString())
            }
        }
    }
    reader.endObject()

    return list.toString()
}

