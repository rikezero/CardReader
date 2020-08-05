package com.example.cardreader.custom


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class RecyclerAdapter<Type : RecyclerView.ViewHolder>(var collection: Collection<*>) :
    RecyclerView.Adapter<Type>() {

    abstract var onTarget: () -> Unit

    abstract fun getTarget(): Int
}

inline fun <reified Builder : ItemViewBuilder<*, *>> recyclerAdapter(collection: Collection<*>, limit: Int = 10) =
    object : RecyclerAdapter<RecyclerViewHolder>(collection) {

        override var onTarget: () -> Unit = {}

        override fun getTarget() = collection.size - limit

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
            Builder::class.java.newInstance().run {
                init(viewGroup, collection)
                RecyclerViewHolder(this)
            }

        override fun getItemCount() = collection.size

        override fun onBindViewHolder(viewHolder: RecyclerViewHolder, position: Int) {
            if (position == getTarget()) {
                onTarget()
            }
            viewHolder.builder.onBind(position)
        }
    }