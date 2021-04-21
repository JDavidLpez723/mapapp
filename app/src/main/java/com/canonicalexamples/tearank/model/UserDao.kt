package com.canonicalexamples.tearank.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    suspend fun create(user: User)

    @Query("SELECT * FROM user_table WHERE email = :email")
    suspend fun get(email: String): User?

}