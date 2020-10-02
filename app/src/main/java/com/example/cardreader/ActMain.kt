package com.example.cardreader

import android.content.DialogInterface
import android.util.TypedValue
import android.view.Gravity
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.marginTop
import androidx.lifecycle.Observer
import com.example.cardreader.base.ActBind
import com.example.cardreader.custom.*
import com.example.cardreader.databinding.ActMainBinding
import com.example.cardreader.model.CardItem
import com.example.cardreader.recycler.ItemViewCard
import com.example.cardreader.viewmodel.ActMainViewModel

class ActMain : ActBind<ActMainBinding>() {
    override val binding: ActMainBinding by viewBind()
    private val viewModel: ActMainViewModel by viewModels()
    private val cards = mutableSetOf<CardItem>()

    override fun ActMainBinding.onBinding() {
        val adapter = recyclerAdapter<ItemViewCard>(cards)
        recycler.adapter = adapter

//        adapter.onTarget = {
//            while (pagina < totalPaginas) {
//                pagina += 1
//                viewModel.getCharacters(pagina)
//            }
//        }

        viewModel.dataSet.observe(this@ActMain, Observer {
            it.run {
                cards.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.getCards()

        viewModel.bulk.observe(this@ActMain, Observer {
            it.run {
                println(uri)
            }
        })

        //viewModel.api()
        update.onClick {
            @Suppress("DEPRECATION")
            val title = TextView(context).apply {
                text = resources.getString(R.string.updateCardBase)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                setTextColor(resources.getColor(R.color.black))
                gravity = Gravity.CENTER
                setPadding(5.dp, 10.dp, 5.dp, 10.dp)
            }
            val dialog = activity.inputDialog(title, R.layout.update_panel)
            dialog.positiveListener = DialogInterface.OnClickListener { dialogInterface, i -> viewModel.api() }
            dialog.show()
        }


    }

}