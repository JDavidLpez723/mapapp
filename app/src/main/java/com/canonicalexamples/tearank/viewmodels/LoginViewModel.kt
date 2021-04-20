package com.canonicalexamples.tearank.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.canonicalexamples.tearank.databinding.FragmentLoginBinding
import com.canonicalexamples.tearank.model.MapDatabase
import com.canonicalexamples.tearank.util.Event
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class LoginViewModel (private val database: MapDatabase): ViewModel() {
    private val _go_to_main_fragment: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val go_to_main_fragment: LiveData<Event<Boolean>> = _go_to_main_fragment

    private val _go_to_reg_fragment: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val go_to_reg_fragment: LiveData<Event<Boolean>> = _go_to_reg_fragment

    private lateinit var mailLogin: String
    private lateinit var passLogin: String

    //Goes to Main Fragment
    fun navigate1(){
        _go_to_main_fragment.value = Event(true)
    }

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
    }

    fun encryptData(data: String): Pair<ByteArray, ByteArray> {
        val cipher: Cipher = Cipher.getInstance("AES/CBC/NoPadding")

        var temp: String = data
        while (temp.toByteArray().size % 16 != 0)
            temp += "\u0020"

        cipher.init(Cipher.ENCRYPT_MODE, getKey())

        val ivBytes = cipher.iv

        val encryptedBytes = cipher.doFinal(temp.toByteArray())

        return Pair(ivBytes, encryptedBytes)
    }

    fun checkKey(): Boolean {
        val keystore: KeyStore = KeyStore.getInstance("AndroidKeyStore")
        keystore.load(null)
        val secretKeyEntry = keystore.getEntry("MyKeyStore", null) as KeyStore.SecretKeyEntry
        return secretKeyEntry.secretKey != null
    }
    fun getKey(): SecretKey {
        val keystore: KeyStore = KeyStore.getInstance("AndroidKeyStore")
        keystore.load(null)
        val secretKeyEntry = keystore.getEntry("MyKeyStore", null) as KeyStore.SecretKeyEntry
        return secretKeyEntry.secretKey
    }

    fun decryptData(ivBytes: ByteArray, data: ByteArray): String{
        val cipher = Cipher.getInstance("AES/CBC/NoPadding")
        val spec = IvParameterSpec(ivBytes)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), spec)
        return cipher.doFinal(data).toString(Charsets.UTF_8).trim()
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