package com.rawa.recyclerswipes.sample.ui.dashboard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rawa.recyclerswipes.sample.ui.dashboard.DashboardItem

class DashboardAdapter() :
    RecyclerView.Adapter<DashboardAdapter.DashboardItemHolder>() {
    private val items = emptyList<DashboardItem>().toMutableList()

    class DashboardItemHolder(private val view: DashboardItemView) : RecyclerView.ViewHolder(view) {
        fun bind(item: DashboardItem, position: Int) {
            view.update(item, position)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardItemHolder {
        // create a new view
        val itemView =
            DashboardItemView(parent.context)
        return DashboardItemHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: DashboardItemHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount() = items.size

    fun update(items: List<DashboardItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    fun getItem(position: Int): DashboardItem {
        return items[position]
    }
}
