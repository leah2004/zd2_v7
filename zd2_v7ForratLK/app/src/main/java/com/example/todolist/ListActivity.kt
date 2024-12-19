package com.example.todolist

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.room.Room

class ListActivity : AppCompatActivity() {
    private lateinit var listContainer: LinearLayout
    private lateinit var database: AppDatabase // Room Database
    private val items = ArrayList<String>() // Список для хранения элементов

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        listContainer = findViewById(R.id.listContainer)

        // Инициализация Room Database
        database = Room.databaseBuilder(this, AppDatabase::class.java, "app_database").allowMainThreadQueries().build()

        // Получаем все элементы из базы данных
        loadItemsFromDatabase()

        // Обновляем представление списка
        updateList()
    }

    private fun loadItemsFromDatabase() {
        val itemList = database.itemDao().getAll() // Получаем все элементы из базы данных
        items.clear()
        items.addAll(itemList.map { it.name }) // Добавляем текст в список
    }

    private fun updateList() {
        listContainer.removeAllViews() // Очищаем контейнер перед обновлением
        for (item in items) {
            val textView = TextView(this)
            textView.text = item
            textView.setTextColor(resources.getColor(android.R.color.white))
            textView.textSize = 18f
            listContainer.addView(textView)
        }
    }
}