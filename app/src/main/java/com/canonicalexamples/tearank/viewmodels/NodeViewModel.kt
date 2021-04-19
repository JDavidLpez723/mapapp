package com.canonicalexamples.tearank.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.canonicalexamples.tearank.model.MapDatabase

class NodeViewModel (private val database: MapDatabase): ViewModel() {
}

class NodeViewModelFactory(private val database: MapDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NodeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}