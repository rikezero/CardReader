package com.example.cardreader.custom

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View

fun Context.inputDialog(title: String, layout: Int) =
    InputDialog(this, title, layout)

fun Context.inputDialog(title: View, layout: Int) =
    InputDialog(this, title, layout)

private const val DEFAULT_TEXT = "Title"

class InputDialog(context: Context, title: Any = DEFAULT_TEXT, layout: Int
) : AlertDialog.Builder(context) {

    var view: View = LayoutInflater.from(getContext()).inflate(layout,null)
    var textPositive = "OK"
    var textNegative = "CANCEL"



    open var positiveListener = DialogInterface.OnClickListener { _, _ -> }
        set(value) {
            field = value
            setPositiveButton(textPositive, positiveListener)
        }
    open var negativeListener = DialogInterface.OnClickListener { _, _ -> }
        set(value) {
            field = value
            setNegativeButton(textNegative, negativeListener)
        }

    init {
        if (title is View){
            setCustomTitle(title)
        }
        else{
            setTitle(title as String)
        }

        setView(view)
        setPositiveButton(textPositive, positiveListener)
        setNegativeButton(textNegative, negativeListener)
    }

}