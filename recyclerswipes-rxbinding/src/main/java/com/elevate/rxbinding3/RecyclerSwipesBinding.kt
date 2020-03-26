package com.elevate.rxbinding3

import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.internal.checkMainThread
import com.rawa.recyclerswipes.OnSwipeListener
import com.rawa.recyclerswipes.RecyclerSwipes
import com.rawa.recyclerswipes.SwipeDirection
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

data class SwipeEvent(val viewHolder: RecyclerView.ViewHolder, val direction: SwipeDirection)

fun RecyclerSwipes.swipes(): Observable<SwipeEvent> {
    return ViewClickObservable(this)
}

private class ViewClickObservable(
    private val swipeCallback: RecyclerSwipes
) : Observable<SwipeEvent>() {

    override fun subscribeActual(observer: Observer<in SwipeEvent>) {
        if (!checkMainThread(observer)) {
            return
        }
        val listener = Listener(swipeCallback, observer)
        observer.onSubscribe(listener)
        swipeCallback.setOnSwipeListener(listener)
    }

    private class Listener(
        private val callback: RecyclerSwipes,
        private val observer: Observer<in SwipeEvent>
    ) : MainThreadDisposable(), OnSwipeListener {


        override fun onSwipe(viewHolder: RecyclerView.ViewHolder, direction: SwipeDirection) {
            if (!isDisposed) {
                observer.onNext(SwipeEvent(viewHolder, direction))
            }
        }

        override fun onDispose() {
            callback.setOnSwipeListener(null)
        }
    }
}

