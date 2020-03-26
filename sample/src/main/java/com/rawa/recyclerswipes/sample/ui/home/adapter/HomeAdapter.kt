package com.rawa.recyclerswipes.sample.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rawa.recyclerswipes.sample.ui.home.HomeItem

class HomeAdapter() :
    RecyclerView.Adapter<HomeAdapter.HomeItemHolder>() {
    private val items = emptyList<HomeItem>().toMutableList()

    class HomeItemHolder(private val view: HomeItemView) : RecyclerView.ViewHolder(view) {
        fun bind(homeItem: HomeItem) {
            view.update(homeItem.word)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeItemHolder {
        // create a new view
        val itemView =
            HomeItemView(parent.context)
        return HomeItemHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: HomeItemHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun update(homeItems: List<HomeItem>) {
        this.items.clear()
        this.items.addAll(homeItems)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }
}
