package com.canonicalexamples.tearank.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.canonicalexamples.tearank.model.MapDatabase
import com.canonicalexamples.tearank.util.Event

class MainViewModel (private val database: MapDatabase): ViewModel() {
    private val _go_to_node_fragment: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val go_to_node_fragment: LiveData<Event<Boolean>> = _go_to_node_fragment

    private val _go_to_history_fragment: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val go_to_history_fragment: LiveData<Event<Boolean>> = _go_to_history_fragment


    //Button1 (Sets parking location)
    fun button1Clicked(){

    }

    //Button2 (Goes to Node Fragment)
    fun button2Clicked(){
        _go_to_node_fragment.value = Event(true)
    }

    //Button3 (Goes to History Fragment)
    fun button3Clicked(){
        _go_to_history_fragment.value = Event(true)
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