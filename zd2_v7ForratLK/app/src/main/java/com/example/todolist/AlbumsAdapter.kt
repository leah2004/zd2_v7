package com.example.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class AlbumsAdapter(
    private val context: Context,
    private val albums: List<Album>,
    private val onAddToFavorites: (Album) -> Unit
) : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.bind(album)
    }

    override fun getItemCount(): Int = albums.size

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val albumTitle: TextView = itemView.findViewById(R.id.albumTitle)
        private val albumArtist: TextView = itemView.findViewById(R.id.albumArtist)
        private val albumCover: ImageView = itemView.findViewById(R.id.albumCover)
        private val btnAddToFavorites: Button = itemView.findViewById(R.id.btnAddToFavorites)

        fun bind(album: Album) {
            albumTitle.text = album.title
            albumArtist.text = album.artist
            Glide.with(context).load(album.coverImage).into(albumCover)

            btnAddToFavorites.setOnClickListener {
                onAddToFavorites(album)
            }
        }
    }
}