package com.canonicalexamples.tearank.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.canonicalexamples.tearank.model.MapDatabase
import com.canonicalexamples.tearank.util.Event

class RegisterViewModel (private val database: MapDatabase): ViewModel()  {
    private val _go_to_main_fragment: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val go_to_main_fragment: LiveData<Event<Boolean>> = _go_to_main_fragment

    private lateinit var mailRegister: String
    private lateinit var passRegister: String

    //Goes to Main Fragment
    fun navigate1(){
        _go_to_main_fragment.value = Event(true)
    }

    fun getRegister(mail:String, pass:String){
        mailRegister = mail
        passRegister = pass
        println(mailRegister)
        println(passRegister)
    }

}

class RegisterViewModelFactory(private val database: MapDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}