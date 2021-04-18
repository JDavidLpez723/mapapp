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
class MainViewModel(private val database: MapDatabase): ViewModel() {
    private val _click: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val click: LiveData<Event<Boolean>> = _click
    var click_index: Int = 0

    var lastItem: MutableLiveData<Int> = MutableLiveData(0)

    fun buttonClicked(index:Int) {
        click_index = index

        //Set Parking
        if(index == 1){
            viewModelScope.launch(Dispatchers.IO){
                val id = database.nodeDao.getMaximumId()
                id?.let {
                    database.nodeDao.create(Node(
                            x = 10.0,
                            y = 10.0,
                            tag = "Nuevo"
                    ))
                }

            }
            _click.value = Event(true)
        }

        //Find my Car
        if (index == 2){
            viewModelScope.launch(Dispatchers.IO){
                val n = database.nodeDao.getMaximumId()
                println("MAXIMUM ID = "+n)
                n?.let {

                }
                lastItem.postValue(n)
            }

        }


    }



}

class MainViewModelFactory(private val database: MapDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}