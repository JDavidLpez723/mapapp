package com.example.map.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.map.model.MapDatabase
import com.example.map.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(private val database: MapDatabase): ViewModel() {

    private var u: User? = null
    private var userList = listOf<User>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            u = database.userDao.getUser(0)
            userList = database.userDao.fetchUsers()
        }
        println("User List:")
        println(userList)
    }

    fun getUser(i: Int): User? {
//        var u : User? = null
//        viewModelScope.launch(Dispatchers.IO) {
//            u = database.userDao.getUser(i)
//        }
//        var u : User? = User(id = 3, email = "aaasa", password = "a")
        println(u)
        return u
    }

    fun getDataExample(): String = "DataExampleFromVIewModel"
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