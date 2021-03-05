package com.example.map.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: User)
    @Query("SELECT * FROM user_table WHERE id = :id")
    suspend fun getUser(id: Int): User?
    @Query("SELECT * FROM user_table")
    suspend fun fetchUsers(): List<User>
    @Update
    suspend fun update(user: User)

}