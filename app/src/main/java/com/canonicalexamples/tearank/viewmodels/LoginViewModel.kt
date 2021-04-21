package com.canonicalexamples.tearank.viewmodels

import android.util.Base64
import android.widget.Toast
import androidx.lifecycle.*
import com.canonicalexamples.tearank.databinding.FragmentLoginBinding
import com.canonicalexamples.tearank.model.MapDatabase
import com.canonicalexamples.tearank.util.Event
import com.canonicalexamples.tearank.util.observeEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec

class LoginViewModel (private val database: MapDatabase): ViewModel() {
    private val _go_to_main_fragment: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val go_to_main_fragment: LiveData<Event<Boolean>> = _go_to_main_fragment

    private val _go_to_reg_fragment: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val go_to_reg_fragment: LiveData<Event<Boolean>> = _go_to_reg_fragment

    private val _incorrect_credentials: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val incorrect_credentials: LiveData<Event<Boolean>> = _incorrect_credentials

    private val _user_not_found: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val user_not_found: LiveData<Event<Boolean>> = _user_not_found

    private lateinit var mailLogin: String
    private lateinit var passLogin: String

    //Goes to Main Fragment
//    fun navigate1(){
//        _go_to_main_fragment.value = Event(true)
//    }

    //Goes to Register Fragment
    fun navigate2(){
        _go_to_reg_fragment.value = Event(true)
    }



    //Button LoginBLoginFragment (Obtain email and password from login input)
    fun getLogin(mail:String, pass:String){
        mailLogin = mail
        passLogin = pass
        println(mailLogin)
        println(passLogin)

        viewModelScope.launch(Dispatchers.IO){
            val u = database.userDao.get(mail)
            if(u!=null) {
                //USER EXISTS
                val c = u.password.split(':')
                val saltEnc = c[1]
                val salt = Base64.decode(saltEnc, Base64.DEFAULT)

                val result = hashText(pass, salt, 12000, 256*8)

                if(c[0]==result.first) {
                    println("LOGIN CORRECT")
                    _go_to_main_fragment.postValue(Event(true))
                }else {
                    println("LOGIN INCORRECT")
                    _incorrect_credentials.postValue(Event(true))
                }

            } else {
                //USER WITH THAT EMAIL DOES NOT EXIST
                println("LOGIN INCORRECT")
                _incorrect_credentials.postValue(Event(true))
            }
        }




    }

    fun hashText(password: String, salt: ByteArray, iterations: Int, keyLength: Int ) : Pair<String, String>{
        val pbKeySpec = PBEKeySpec(password.toCharArray(), salt, iterations, keyLength) // 1
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256") // 2
        val key = secretKeyFactory.generateSecret(pbKeySpec)

        val result = Base64.encodeToString(key.encoded, Base64.DEFAULT).dropLast(1)
        val saltencoded = Base64.encodeToString(salt, Base64.DEFAULT).dropLast(1)
        return Pair(result,saltencoded)
    }


}


class LoginViewModelFactory(private val database: MapDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}