package com.example.cardreader


import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.cardreader.base.ActBind
import com.example.cardreader.custom.recyclerAdapter
import com.example.cardreader.custom.viewBind
import com.example.cardreader.databinding.ActMainBinding
import com.example.cardreader.model.CardItem
import com.example.cardreader.model.DefaultCardsResponse
import com.example.cardreader.recycler.ItemViewCard
import com.example.cardreader.viewmodel.ActMainViewModel
import com.google.gson.Gson

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

        viewModel.api()


    }

}