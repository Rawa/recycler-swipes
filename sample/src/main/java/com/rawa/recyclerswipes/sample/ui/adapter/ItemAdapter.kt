package com.rawa.recyclerswipes.sample.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rawa.recyclerswipes.sample.ui.home.HomeItem

class ItemAdapter() :
    RecyclerView.Adapter<ItemAdapter.HomeItemHolder>() {
    private val items = emptyList<HomeItem>().toMutableList()

    class HomeItemHolder(private val view: ItemView) : RecyclerView.ViewHolder(view) {
        fun bind(item: HomeItem) {
            view.update(item.word)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeItemHolder {
        // create a new view
        val itemView =
            ItemView(parent.context)
        return HomeItemHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: HomeItemHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun update(items: List<HomeItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }
}
