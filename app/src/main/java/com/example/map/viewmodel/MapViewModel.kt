package com.example.map.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.map.model.MapDatabase
import com.example.map.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(private val database: MapDatabase): ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    fun getUser(i: Int): User? {
        var u : User? = null
        viewModelScope.launch(Dispatchers.IO) {
            u = database.userDao.getUser(i)
        }
//        var u : User? = User(id = 3, email = "aaasa", password = "a")
        return u
    }
}

class MapViewModelFactory(private val database: MapDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}