package com.rawa.recyclerswipes.sample.ui.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.rawa.recyclerswipes.sample.R
import kotlinx.android.synthetic.main.adapter_item.view.*

class ItemView(context: Context, attr: AttributeSet?, style: Int) :
    ConstraintLayout(context, attr, style) {
    constructor(context: Context, attr: AttributeSet) : this(context, attr, 0)
    constructor(context: Context) : this(context, null, 0)

    init {
        LayoutInflater.from(context).inflate(R.layout.adapter_item, this)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    fun update(text: String) {
        tv_adapter_item.text = text
    }
}