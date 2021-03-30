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
class MainViewModel(): ViewModel() {
    private val _click: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val click: LiveData<Event<Boolean>> = _click
    private val _click_index: MutableLiveData<Event<Int>> = MutableLiveData()
    val click_index: LiveData<Event<Int>> = _click_index

    fun buttonClicked(index:Int) {
        _click.value = Event(true)
        _click_index.value = Event(index)
        viewModelScope.launch(Dispatchers.IO) {
            //database.nodeDao.create(node = Node(x=800.0, y=-20.0, tag="tag"))
        }
    }

}

class MainViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}