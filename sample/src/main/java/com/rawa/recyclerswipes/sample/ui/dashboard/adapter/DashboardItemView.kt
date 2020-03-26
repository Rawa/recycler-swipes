package com.rawa.recyclerswipes.sample.ui.dashboard.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.rawa.recyclerswipes.sample.R
import com.rawa.recyclerswipes.sample.ui.dashboard.DashboardItem
import com.rawa.recyclerswipes.sample.ui.defaultAttr
import kotlinx.android.synthetic.main.dashboard_item.view.*

class DashboardItemView(
    context: Context,
    attr: AttributeSet? = context.defaultAttr(R.layout.dashboard_item),
    style: Int
) :
    ConstraintLayout(context, attr, style) {
    constructor(context: Context, attr: AttributeSet) : this(context, attr, 0)
    constructor(context: Context) : this(context, null, 0)

    init {
        LayoutInflater.from(context).inflate(R.layout.dashboard_item, this)
    }

    fun update(item: DashboardItem, position: Int) {
        tv_adapter_item.text = item.word
        val color = if (((position / 4) % 2 + position) % 2 == 0) {
            R.color.colorPrimary
        } else
            R.color.colorPrimaryDark
        setBackgroundColor(resources.getColor(color, null))
    }
}
