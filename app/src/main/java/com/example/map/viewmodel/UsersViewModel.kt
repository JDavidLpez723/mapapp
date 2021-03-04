package com.example.map.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.map.model.MapDatabase
import com.example.map.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel(private val database: MapDatabase): ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {


        }
    }

    suspend fun getUser(i: Int): User? {
        return database.userDao.getUser(i)
    }
}