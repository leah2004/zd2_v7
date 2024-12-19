package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Albums : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlbumsAdapter
    private val albums = mutableListOf<Album>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)

        recyclerView = findViewById(R.id.recyclerViewAlbums)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = AlbumsAdapter(this, albums) { album ->
            addToFavorites(album)
        }
        recyclerView.adapter = adapter

        fetchAlbums()

    }

    private fun fetchAlbums() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.discogs.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(DiscogsApi::class.java)
        api.searchAlbums("rock").enqueue(object : Callback<DiscogsResponse> {
            override fun onResponse(call: Call<DiscogsResponse>, response: Response<DiscogsResponse>) {
                if (response.isSuccessful) {
                    val results = response.body()?.results ?: emptyList()
                    albums.clear()
                    albums.addAll(results.map {
                        Album(it.id, it.title, "Unknown Artist", it.cover_image)
                    })
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@Albums, "Ошибка: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DiscogsResponse>, t: Throwable) {
                Toast.makeText(this@Albums, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun addToFavorites(album: Album) {
        // Инициализация базы данных
        val database = Room.databaseBuilder(this, AppDatabase::class.java, "app_database")
            .allowMainThreadQueries()
            .build()

        // Проверяем, есть ли альбом в избранном
        val existingItem = database.itemDao().getByName(album.title)
        if (existingItem != null) {
            // Если альбом уже есть, показываем сообщение
            Toast.makeText(this, "Этот альбом уже в избранном", Toast.LENGTH_SHORT).show()
        } else {
            // Если альбома нет, добавляем его в избранное
            database.itemDao().insert(Item(name = album.title))
            Toast.makeText(this, "Добавлено в избранное", Toast.LENGTH_SHORT).show()
        }
    }


}