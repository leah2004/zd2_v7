package com.example.todolist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun list(view: View) {
        val text2 = intent.getStringExtra("ITEM_TEXT")
        if (text2 != null) {
            // Сохраняем текст в SharedPreferences
            val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val currentText = sharedPreferences.getString("ITEM_TEXT", "") ?: ""
            editor.putString("ITEM_TEXT", currentText + text2 + "\n")
            editor.apply()
        }

        // Переход на экран списка
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }

    fun images(view: View) {
        val intent = Intent(this, Albums::class.java)
        startActivity(intent)
    }


    fun login(view: View) {

        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

    companion object {
        var text = ""
    }
}