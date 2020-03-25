package com.rawa.recyclerswipes.sample.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<List<HomeItem>>().apply {
        value =
            """Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.""".split(
                "\\s".toRegex()
            ).mapIndexed { a, b -> HomeItem(a.toLong(), b) }

    }
    val text: LiveData<List<HomeItem>> = _text

    fun deleteWord(wordId: Long) {
        _text.value = _text.value!!.filterNot { it.id == wordId }
    }
}

data class HomeItem(val id: Long, val word: String)