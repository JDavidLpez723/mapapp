package com.canonicalexamples.mapapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.canonicalexamples.mapapp.model.MapDatabase
import com.canonicalexamples.mapapp.model.Node
import com.canonicalexamples.mapapp.model.TileService
import com.canonicalexamples.mapapp.util.Event
import com.canonicalexamples.mapapp.view.NodeFragmentDirections
import com.canonicalexamples.mapapp.view.NodesListFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.*

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
class NodesListViewModel(private val database: MapDatabase, private val webservice: TileService): ViewModel() {
    private val _navigate: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val navigate: LiveData<Event<Boolean>> = _navigate
    private val _navigate_index: MutableLiveData<Event<Int>> = MutableLiveData()
    val navigate_index: LiveData<Event<Int>> = _navigate_index
    private val _open_node: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val open_node: LiveData<Event<Boolean>> = _open_node
    private var nodesList = listOf<Node>()
    data class Item(val name: String)

    public var itemSelected = 0

    init {
        viewModelScope.launch(Dispatchers.IO) {
            nodesList = database.nodeDao.fetchNodes()
        }
    }

//    fun init_list(){
//        viewModelScope.launch(Dispatchers.IO) {
//            nodesList = database.nodeDao.fetchNodes()
//        }
//    }


    val numberOfItems: Int
        get() = nodesList.count()

    //


    //Button for adding a new node
    fun addButtonClicked(index:Int) {
        _navigate.value = Event(true)
        _navigate_index.value = Event(index)
        viewModelScope.launch(Dispatchers.IO) {
            //database.nodeDao.create(node = Node(x=800.0, y=-20.0, tag="tag"))
        }
    }

    fun getItem(n: Int) = Item(name = nodesList[n].x.toString() + ", " + nodesList[n].y.toString()
                                + " " +nodesList[n].tag)
    fun getNode(i: Int): Node = nodesList[i]

    //When an item of the adapter list has been clicked:
    fun onClickItem(n: Int) {
        println("Item $n clicked")
        /*viewModelScope.launch(Dispatchers.IO) {
            val todo = webservice.getTodo(n).await()
            println("todo: ${todo.title}")
        }*/

        //Pass Selected Item index to other view Model
        //val action = NodesListFragmentDirections.ActionFirstFragmentToNodeFragment()
        itemSelected = n
        _open_node.value = Event(true)
    }

    fun getXYTile(lat : Double, lon: Double, zoom : Int) : Pair<Int, Int> {
        val latRad = Math.toRadians(lat)
        var xtile = floor( (lon + 180) / 360 * (1 shl zoom) ).toInt()
        var ytile = floor( (1.0 - asinh(tan(latRad)) / PI) / 2 * (1 shl zoom) ).toInt()

        if (xtile < 0) {
            xtile = 0
        }
        if (xtile >= (1 shl zoom)) {
            xtile= (1 shl zoom) - 1
        }
        if (ytile < 0) {
            ytile = 0
        }
        if (ytile >= (1 shl zoom)) {
            ytile = (1 shl zoom) - 1
        }
        println("https://a.tile.openstreetmap.org/"+zoom+"/"+xtile+"/"+ytile+".png")
        return Pair(xtile, ytile)
    }


}

class NodesListViewModelFactory(private val database: MapDatabase, private val webservice: TileService): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NodesListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NodesListViewModel(database, webservice) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}