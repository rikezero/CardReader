package com.example.cardreader.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding

abstract class ActBind<Binding : ViewBinding> : ActBase() {

    abstract val binding: Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.onBinding()
    }

    open fun Binding.onBinding() {}

}