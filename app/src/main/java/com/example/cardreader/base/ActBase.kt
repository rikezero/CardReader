package com.example.cardreader.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity


open class ActBase(open val layout: Int = 0) : AppCompatActivity(), ContextFinder {

    open val view: Any? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.extras?.onExtras()
        if (layout != 0) {
            setContentView(layout)
            ((window.decorView.rootView as ViewGroup).getChildAt(0) as ViewGroup).onView()
        } else if (view is View) {
            setContentView(view as View)
            (view as ViewGroup).onView()
        }
    }

    open fun Bundle.onExtras() {}

    open fun ViewGroup.onView() {}
}