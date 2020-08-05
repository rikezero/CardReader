package com.example.cardreader.recycler



import com.example.cardreader.custom.ItemViewBuilder
import com.example.cardreader.databinding.ItemListCardBinding
import com.example.cardreader.model.CardItem


class ItemViewCard : ItemViewBuilder<CardItem, ItemListCardBinding>() {

    override val binding by lazy { bind(ItemListCardBinding::class) }

    override fun ItemListCardBinding.onBind(position: Int) {

        (collection as Set<CardItem>).elementAt(position).run {
            //Picasso.get().load(image).into(itemPersonaImage)
            itemName.text = name
            itemType.text = type_line
        }
    }

}
