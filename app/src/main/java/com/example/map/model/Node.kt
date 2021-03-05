package com.example.map.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "node_table")
data class Node(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val x: Double = 0.0,
    val y: Double = 0.0,
    val tag: String = "",
){
}