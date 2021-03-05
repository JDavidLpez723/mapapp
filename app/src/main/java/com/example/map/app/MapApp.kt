package com.example.map.app

import android.app.Application
import com.example.map.model.MapDatabase
import com.example.map.model.User
import com.example.map.model.UserDao
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Application: Class shared among every fragment and activity
//it is where the db is stored
class MapApp: Application() {
    val database by lazy { MapDatabase.getInstance(this)}
    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            database.clearAllTables()
            database.userDao.apply {
                this.addUser(user = User(id = 0, email = "emailuser@gmail.com", password = "pass1"))
                this.addUser(user = User(id = 1, email = "emailuser2@gmail.com", password = "pass2"))
            }
        }
    }
}