package com.example.cardreader.custom

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.example.cardreader.base.ContextFinder
import com.example.cardreader.model.CardItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import java.io.*
import java.lang.reflect.Type
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

fun Context.writeFile(fileName:String, body: ResponseBody?){
    var input: InputStream? = null
    val outputStream:FileOutputStream
    if (body != null) {
        try {
            input = body.byteStream()
            outputStream = openFileOutput(fileName,Context.MODE_PRIVATE)
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
    }
    else{
        return
    }
}


fun saveFile(body: ResponseBody?, pathToSave: String):String{
    if (body==null)
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
    }catch (e:Exception){
        Log.e("saveFile",e.toString())
    }
    finally {
        input?.close()
    }
    return ""
}

fun Context.updateDatabase(fileName:String){
    val file = File(filesDir,fileName)
    if (file.exists()){
        val cardList = mutableListOf<CardItem>()
        val fis = openFileInput(fileName)
        val reader = BufferedReader(InputStreamReader(fis))
        val data = StringBuilder()
        val line: String?

        line = reader.readLine()

        while (line != null) {
            data.append(line).append("\n")
        }

        data.toString()

        reader.close()
        fis.close()

        val walletListType: Type = object : TypeToken<ArrayList<WalletClass?>?>() {}.type
        walletList.add(Gson().fromJson(data, walletListType))
    }
}

