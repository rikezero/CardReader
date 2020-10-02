package com.example.cardreader.custom

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.JsonReader
import android.util.JsonToken
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.example.cardreader.base.ContextFinder
import com.example.cardreader.model.CardItem
import com.example.cardreader.repositpory.RepositoryDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import okio.Utf8
import java.io.*
import kotlin.reflect.KClass
import kotlin.reflect.KFunction0

val Context.inflater get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

@Suppress("UNCHECKED_CAST")
inline fun <reified Binding : ViewBinding> ContextFinder.viewBind() = lazy {
    Binding::class.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, cont.inflater) as Binding
}
@Suppress("UNCHECKED_CAST") // Converts Pixel value to DensityPixel value
val <N : Number> N.dp
    get() = (toFloat() * Resources.getSystem().displayMetrics.density) as N
val <N : Number> N.sp
    get() = (toFloat() * Resources.getSystem().displayMetrics.scaledDensity) as N

@Suppress("UNCHECKED_CAST")
fun <Binding : ViewBinding> Context.viewBind(klass: KClass<Binding>) =
    klass.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, inflater) as Binding

fun <T : Any> Context.launchActivity(clazz: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, clazz)
    startActivity(intent.putExtras(Bundle().apply(extras)))
}

fun <V : View> V.onClick(function: V.() -> Unit = {}) {
    setOnClickListener { function() }
}

fun <V : View> V.onClick(function: KFunction0<*>) {
    setOnClickListener { function() }
}

val Context.activity: Activity
    get() = when (this) {
        is Activity -> this
        else -> (this as ContextWrapper).baseContext.activity
    }

fun saveFile(body: ResponseBody?, pathToSave: String): String {
    if (body == null)
        return ""
    var input: InputStream? = null
    try {
        input = body.byteStream()
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
        val reader = InputStreamReader(inputStream, "UTF-8")
        val jReader = JsonReader(reader)
        jReader.use { jReader ->
            jReader.beginArray()
            while (jReader.hasNext()) {
                cardList.add(readCard(jReader))
            }
            jReader.endArray()
        }
        println(cardList[0].name)
        println(cardList[0].scryfall_uri)
        println(cardList.size)
        CoroutineScope(Dispatchers.Default).launch {
           cardList.forEach { card ->
               RepositoryDatabase(applicationContext).updateCards(card)
           }
        }
    }
}

fun readCard(reader: JsonReader): CardItem {
    var artist = ""
    var artistIds = ""
    var cardBackId = ""
    var cmc = ""
    var colorIdentity = ""
    var colors = ""
    var id = ""
    var illustrationId = ""
    var imageUris = ""
    var keywords = ""
    var lang = ""
    var legalities = ""
    var manaCost = ""
    var name = ""
    var objekt = ""
    var oracleText = ""
    var power = ""
    var producedMana = ""
    var rarity = ""
    var rulingsUri = ""
    var scryfallSetUri = ""
    var scryfallUri = ""
    var set = ""
    var setName = ""
    var setSearchUri = ""
    var setUri = ""
    var toughness = ""
    var typeLine = ""
    var uri = ""

    reader.beginObject()
    while (reader.hasNext()) {
        val card: String = reader.nextName()
        when {
            card.equals("object") -> {
                objekt = reader.nextString()
            }
            card.equals("id") -> {
                id = reader.nextString()
            }
            card.equals("name") -> {
                name = reader.nextString()
            }
            card.equals("lang") -> {
                lang = reader.nextString()
            }
            card.equals("uri") -> {
                uri = reader.nextString()
            }
            card.equals("image_uris") && reader.peek() != JsonToken.NULL -> {
                imageUris = readImageArray(reader)
            }
            card.equals("uri") -> {
                uri = reader.nextString()
            }
            card.equals("mana_cost") -> {
                manaCost = reader.nextString()
            }
            card.equals("cmc") -> {
                cmc = reader.nextString()
            }
            card.equals("type_line") -> {
                typeLine = reader.nextString()
            }
            card.equals("oracle_text") -> {
                oracleText = reader.nextString()
            }
            card.equals("power") -> {
                power = reader.nextString()
            }
            card.equals("toughness") -> {
                toughness = reader.nextString()
            }
            card.equals("colors") && reader.peek() != JsonToken.NULL -> {
                colors = readStringArray(reader)
            }
            card.equals("color_identity") && reader.peek() != JsonToken.NULL -> {
                colorIdentity = readStringArray(reader)
            }
            card.equals("keywords") && reader.peek() != JsonToken.NULL -> {
                keywords = readStringArray(reader)
            }
            card.equals("legalities") -> {
                legalities = readStringObj(reader)
            }
            card.equals("set") -> {
                set = reader.nextString()
            }
            card.equals("set_name") -> {
                setName = reader.nextString()
            }
            card.equals("set_uri") -> {
                setUri = reader.nextString()
            }
            card.equals("set_search_uri") -> {
                setSearchUri = reader.nextString()
            }
            card.equals("rulings_uri") -> {
                rulingsUri = reader.nextString()
            }
            card.equals("artist") -> {
                artist = reader.nextString()
            }
            card.equals("artist_ids") -> {
                artistIds = readStringArray(reader)
            }
            card.equals("illustration_id") -> {
                illustrationId = reader.nextString()
            }
            card.equals("card_back_id") -> {
                cardBackId = reader.nextString()
            }
            card.equals("rarity") -> {
                rarity = reader.nextString()
            }
            card.equals("scryfall_set_uri") -> {
                scryfallSetUri = reader.nextString()
            }
            card.equals("scryfall_uri") -> {
                scryfallUri = reader.nextString()
            }
            card.equals("produced_mana") -> {
                producedMana = readStringArray(reader)
            }
            else -> {
                reader.skipValue();
            }
        }
    }
    reader.endObject()

    return CardItem(
        artist, artistIds, cardBackId, cmc, colorIdentity, colors, id,
        illustrationId, imageUris, keywords, lang, legalities, manaCost, name, objekt,
        oracleText, power, producedMana, rarity, rulingsUri, scryfallSetUri, scryfallUri,
        set, setName, setSearchUri, setUri, toughness, typeLine, uri
    )
}

fun readStringArray(reader: JsonReader): String {
    val list: ArrayList<String> = ArrayList()
    val returnString: String

    reader.beginArray()
    while (reader.hasNext()) {
        list.add(reader.nextString())
    }
    reader.endArray()

    returnString = list.toString().removeSurrounding("[", "]")

    return returnString
}

fun readStringObj(reader: JsonReader): String {
    var returnString = ""
    reader.beginObject()
    while (reader.hasNext()) {
        val format = "\"" + reader.nextName() + "\""
        val legality = "\"" + reader.nextString() + "\""

        returnString += "$format:$legality,"
    }
    reader.endObject()

    returnString = returnString.substringBeforeLast(",")
    returnString = "{$returnString}"


    return returnString
}

fun readImageArray(reader: JsonReader): String {
    val list: ArrayList<String> = ArrayList()
    val returnString: String

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

    returnString = list.toString().removeSurrounding("[", "]")

    return returnString

}




