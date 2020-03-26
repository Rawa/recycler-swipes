package com.rawa.recyclerswipes.sample.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elevate.rxbinding3.swipes
import com.rawa.recyclerswipes.RecyclerSwipes
import com.rawa.recyclerswipes.SwipeDirection
import com.rawa.recyclerswipes.attachTo
import com.rawa.recyclerswipes.sample.R
import com.rawa.recyclerswipes.sample.ui.dashboard.adapter.DashboardAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class DashboardFragment : Fragment() {

    private val dashboardViewModel: DashboardViewModel by viewModels()

    private lateinit var swipes: RecyclerSwipes
    private lateinit var adapter: DashboardAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        adapter = DashboardAdapter()
        val recyclerView: RecyclerView = root.findViewById(R.id.rv_dashboard_items)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 4, RecyclerView.HORIZONTAL, false)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })


        swipes = RecyclerSwipes(
            SwipeDirection.UP to R.layout.swipe_peekabo,
            SwipeDirection.DOWN to R.layout.swipe_peekabo
        )

        swipes.attachTo(recyclerView)
        return root
    }

    lateinit var disposable: Disposable
    override fun onResume() {
        super.onResume()
        disposable = swipes.swipes().observeOn(AndroidSchedulers.mainThread()).subscribe {
            when (it.direction) {
                SwipeDirection.UP, SwipeDirection.DOWN -> {
                    val item = adapter.getItem(it.viewHolder.adapterPosition)
                    Toast.makeText(context, "Peekabo ${item.word}!", Toast.LENGTH_SHORT).show()
                    dashboardViewModel.deleteWord(item.id)
                }
                else -> {
                    throw UnsupportedOperationException()
                }
            }

        }
    }

    override fun onPause() {
        super.onPause()
        disposable.dispose()
    }
}
