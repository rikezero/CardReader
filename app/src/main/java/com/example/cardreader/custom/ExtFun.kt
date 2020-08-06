package com.example.cardreader.custom

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.example.cardreader.base.ContextFinder
import okhttp3.ResponseBody
import java.io.FileOutputStream
import java.io.InputStream
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