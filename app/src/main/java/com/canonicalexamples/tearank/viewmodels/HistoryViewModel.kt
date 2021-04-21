package com.canonicalexamples.tearank.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.canonicalexamples.tearank.model.Node
import com.canonicalexamples.tearank.model.MapDatabase
import com.canonicalexamples.tearank.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.asinh
import kotlin.math.floor
import kotlin.math.tan

/**
 * 20210210. Initial version created by jorge
 * for a Canonical Examples training.
 *
 * Copyright 2021 Jorge D. Ortiz Fuentes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class HistoryViewModel(private val database: MapDatabase): ViewModel() {
    private val _go_to_node_fragment: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val go_to_node_fragment: LiveData<Event<Boolean>> = _go_to_node_fragment

    var itemSelected = -1

    private var nodesList = listOf<Node>()
    data class Item(val name: String)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            nodesList = database.nodeDao.fetchNodes()
        }
    }

    val numberOfItems: Int
        get() = nodesList.count()

    fun addButtonClicked() {
//        _navigate.value = Event(true)
    }

    fun getItem(n: Int) = Item(name = nodesList[n].x.toString() + ", " + nodesList[n].y.toString()
            + " " +nodesList[n].tag)
    fun getNode(i: Int): Node = nodesList[i]

    fun onClickItem(n: Int) {
//        println("Item $n clicked")
        itemSelected = n
        _go_to_node_fragment.value = Event(true)
    }




}

class HistoryViewModelFactory(private val database: MapDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
