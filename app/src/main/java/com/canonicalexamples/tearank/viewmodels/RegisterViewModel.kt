package com.canonicalexamples.tearank.viewmodels

import android.util.Base64
import androidx.lifecycle.*
import com.canonicalexamples.tearank.model.MapDatabase
import com.canonicalexamples.tearank.model.User
import com.canonicalexamples.tearank.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.SecureRandom
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

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



        val random = SecureRandom()
        val salt = ByteArray(256)
        random.nextBytes(salt)

        val itCount = 12000
        val pbKeySpec = PBEKeySpec(pass.toCharArray(), salt, itCount, 256*8) // 1
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256") // 2
        val key = secretKeyFactory.generateSecret(pbKeySpec)

        val result = Base64.encodeToString(key.encoded, Base64.DEFAULT)
        val saltencoded = Base64.encodeToString(salt, Base64.DEFAULT)

//        println("Result:")
//        print(result)
//        println("Salt:")
//        print(saltencoded)

        viewModelScope.launch (Dispatchers.IO){
            database.userDao.create(User(email = mail, password = result+":"+saltencoded))
            val user = database.userDao.get(mail)
            print(user.toString())
        }


        //print (secretKeyFactory.generateSecret(pbKeySpec))
//        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded // 3
//        val keySpec = SecretKeySpec(keyBytes, "AES") // 4


//        val salt: ByteArray
//        val secRamdom = SecureRandom.getInstance()
//
//        secRamdom.nextBytes(salt)
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