package com.example.todolist

import android.content.ClipData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todolist.Item

@Dao
interface ItemDao {
    @Insert
    fun insert(item: Item)

    @Query("SELECT * FROM items")
    fun getAll(): List<Item>

    @Query("SELECT * FROM items WHERE name = :name LIMIT 1")
    fun getByName(name: String): Item?
}