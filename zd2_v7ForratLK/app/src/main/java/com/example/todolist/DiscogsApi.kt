package com.example.todolist

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscogsApi {
    @GET("database/search")
    fun searchAlbums(
        @Query("q") query: String,
        @Query("type") type: String = "release",
        @Query("token") token: String = "rZYplRcPsTaNTxZGjeEzInakUZHVSeJwrqkCzRep"
    ): Call<DiscogsResponse>
}

data class DiscogsResponse(
    val results: List<AlbumResult>
)

data class AlbumResult(
    val id: Int,
    val title: String,
    val cover_image: String,
    val user_data: UserData
)

data class UserData(
    val in_collection: Boolean
)