package com.redbreadplease.cyclop.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.retrofit.pojos.GalleryPhoto
import com.squareup.picasso.Picasso

class GalleryRecyclerViewAdapter(
    private val photos: MutableList<GalleryPhoto?>,
    private val UIContext: Context
) : RecyclerView.Adapter<GalleryRecyclerViewAdapter.ViewHolder>() {

    private var lastElementId: Int = 0

    override fun getItemCount(): Int = photos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.gallery_photo_view, parent, false)
        return ViewHolder(itemView, lastElementId++)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var url: String
        var annotation: String?
        photos[position]!!.also {
            url = it.url!!
            annotation = it.annotation
        }
        holder.also {
            Picasso.with(UIContext).load(url).into(it.imageView)
            it.imageDescription.text = annotation
        }
    }

    class ViewHolder(itemView: View, val id: Int) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.gallery_photo)
        var imageDescription: TextView = itemView.findViewById(R.id.gallery_photo_description)
    }

    fun addPhoto(photo: GalleryPhoto) {
        photos.add(photo)
    }
}