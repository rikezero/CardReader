package com.example.cardreader.custom

import android.content.Context
import android.content.DialogInterface.OnShowListener
import androidx.appcompat.app.AppCompatDialog
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window.FEATURE_NO_TITLE
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.example.cardreader.R
import java.io.InvalidClassException

@Suppress("DEPRECATION")
fun View.newPanel(
  layout: Any = R.layout.panel ,
  onShow: OnShowListener? = null,
  init: (Panel.() -> Unit) = {}
) = Panel(context, onShow, init, layout).apply {
  show()
}

class Panel(
  context: Context,
  onShow: OnShowListener? = null,
  init: (Panel.() -> Unit),
  layout: Any
) : AppCompatDialog(context, R.style.Panel) {

  init {
    requestWindowFeature(FEATURE_NO_TITLE)
    when (layout) {
      is Int  -> setContentView(layout)
      is View -> setContentView(layout)
      else    -> throw InvalidClassException("layout must be resourceID or View")
    }
    init(this)
    setOnShowListener(onShow)
  }
}