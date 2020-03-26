package com.rawa.recyclerswipes.sample.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rawa.recyclerswipes.OnSwipeListener
import com.rawa.recyclerswipes.RecyclerSwipes
import com.rawa.recyclerswipes.SwipeDirection
import com.rawa.recyclerswipes.attachTo
import com.rawa.recyclerswipes.sample.R
import com.rawa.recyclerswipes.sample.ui.home.adapter.HomeAdapter

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.rv_home_items)
        adapter = HomeAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })


        val swipes = RecyclerSwipes(
            SwipeDirection.LEFT to R.layout.swipe_left_block,
            SwipeDirection.RIGHT to R.layout.swipe_right_delete
        )

        swipes.attachTo(recyclerView)
        swipes.setOnSwipeListener { vH: RecyclerView.ViewHolder, dir: SwipeDirection ->
            when (dir) {
                SwipeDirection.LEFT -> {
                    val id = adapter.getItemId(vH.adapterPosition)
                    Toast.makeText(context, "Word blocked", Toast.LENGTH_SHORT).show()
                    homeViewModel.deleteWord(id)
                }
                SwipeDirection.RIGHT -> {
                    val id = adapter.getItemId(vH.adapterPosition)
                    Toast.makeText(context, "Word deleted", Toast.LENGTH_SHORT).show()
                    homeViewModel.deleteWord(id)
                }
                else -> {
                    throw UnsupportedOperationException("$dir is not supported")
                }
            }
        }

        return root
    }
}
