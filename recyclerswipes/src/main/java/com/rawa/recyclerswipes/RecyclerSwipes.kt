package com.rawa.recyclerswipes

import android.content.Context
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.content.getSystemService
import androidx.core.graphics.withTranslation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


class RecyclerSwipes(private val swipeLayouts: Map<SwipeDirection, Int>) :
    ItemTouchHelper.SimpleCallback(0, swipeLayouts.swipeDirs()) {
    constructor(vararg swipeLayouts: Pair<SwipeDirection, Int>) : this(swipeLayouts.toMap())

    private var listener: OnSwipeListener? = null

    private lateinit var swipeViewMap: Map<SwipeDirection, View>

    init {
        require(swipeLayouts.isNotEmpty()) {
            "Swipe bindings cannot be empty"
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView: View = viewHolder.itemView

        if (!::swipeViewMap.isInitialized) {
            swipeViewMap = swipeLayouts.map { (direction, layout) ->
                direction to inflateLayout(itemView.context, layout)
            }.toMap()
        }

        when {
            // canvas, swipeView, width, height, dY, dX
            dX > 0 && swipeViewMap.containsKey(SwipeDirection.RIGHT) -> {
                val swipeView = swipeViewMap.getValue(SwipeDirection.RIGHT)
                val width = min(dX.toInt(), itemView.width)
                renderSwipeView(
                    swipeView,
                    c,
                    width,
                    itemView.height,
                    itemView.left.toFloat(),
                    itemView.top.toFloat()
                )
            }
            dY > 0 && swipeViewMap.containsKey(SwipeDirection.DOWN) -> {
                // Swipe to down
                val swipeView = swipeViewMap.getValue(SwipeDirection.DOWN)
                val height = min(dY.toInt(), itemView.height)
                renderSwipeView(
                    swipeView,
                    c,
                    itemView.width,
                    height,
                    itemView.left.toFloat(),
                    itemView.top.toFloat()
                )
            }
            dX < 0 && swipeViewMap.containsKey(SwipeDirection.LEFT) -> {
                // Swipe to left
                val swipeView = swipeViewMap.getValue(SwipeDirection.LEFT)
                val width = min(abs(dX.toInt()), itemView.width)
                val transX = max(itemView.right + dX, itemView.left.toFloat())
                renderSwipeView(
                    swipeView,
                    c,
                    width,
                    itemView.height,
                    transX,
                    itemView.top.toFloat()
                )
            }
            dY < 0 && swipeViewMap.containsKey(SwipeDirection.UP) -> {
                // Swipe to up
                val swipeView = swipeViewMap.getValue(SwipeDirection.UP)
                val height = min(abs(dY.toInt()), itemView.height)
                val transY = max(itemView.bottom.toFloat() + dY, itemView.top.toFloat())
                renderSwipeView(
                    swipeView,
                    c,
                    itemView.width,
                    height,
                    itemView.left.toFloat(),
                    transY
                )
            }
            else -> {
                // view is reset
                c.clipRect(itemView.left, itemView.top, itemView.right, itemView.bottom)
            }
        }
    }

    private fun renderSwipeView(
        swipeView: View,
        c: Canvas,
        width: Int,
        height: Int,
        transX: Float,
        transY: Float
    ) {
        swipeView.measure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        )
        swipeView.layout(0, 0, width, height)

        c.withTranslation(transX, transY) {
            // Clip so view does not draw outside of intended layout
            c.clipRect(0, 0, width, height)
            swipeView.draw(c)
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener?.onSwipe(viewHolder, SwipeDirection.valueOf(direction))
    }

    fun setOnSwipeListener(listener: OnSwipeListener?) {
        this.listener = listener
    }

    fun setOnSwipeListener(lambda: ((viewHolder: RecyclerView.ViewHolder, direction: SwipeDirection) -> Unit)) {
        setOnSwipeListener(object : OnSwipeListener {
            override fun onSwipe(viewHolder: RecyclerView.ViewHolder, direction: SwipeDirection) {
                lambda.invoke(viewHolder, direction)
            }

        })
    }

    private fun inflateLayout(context: Context, @LayoutRes layout: Int): View {
        val li: LayoutInflater = context.getSystemService()!!
        return li.inflate(layout, null, false)
    }
}

fun Map<SwipeDirection, Int>.swipeDirs(): Int = keys.map { it.swipeDir }.fold(0, { a, b -> a or b })

interface OnSwipeListener {
    fun onSwipe(viewHolder: RecyclerView.ViewHolder, direction: SwipeDirection)
}

enum class SwipeDirection(val swipeDir: Int) {
    UP(ItemTouchHelper.UP),
    DOWN(ItemTouchHelper.DOWN),
    LEFT(ItemTouchHelper.LEFT),
    RIGHT(ItemTouchHelper.RIGHT);

    companion object {
        fun valueOf(direction: Int): SwipeDirection = when (direction) {
            ItemTouchHelper.LEFT -> LEFT
            ItemTouchHelper.UP -> UP
            ItemTouchHelper.RIGHT -> RIGHT
            ItemTouchHelper.DOWN -> DOWN
            else -> throw IllegalArgumentException("Swipe direction '$this' not supported")
        }
    }
}

fun RecyclerSwipes.attachTo(recyclerView: RecyclerView) {
    val ith = ItemTouchHelper(this)
    ith.attachToRecyclerView(recyclerView)
}

