package com.canonicalexamples.tearank.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.canonicalexamples.tearank.model.MapDatabase

class MainViewModel (private val database: MapDatabase): ViewModel() {

}

class MainViewModelFactory(private val database: MapDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}