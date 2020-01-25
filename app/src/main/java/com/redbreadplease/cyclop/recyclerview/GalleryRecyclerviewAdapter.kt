package com.redbreadplease.cyclop.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.retrofit.pojos.GalleryPhoto
import com.squareup.picasso.Picasso

class GalleryRecyclerviewAdapter(
    private val photos: MutableList<GalleryPhoto?>,
    private val applicationContext: Context
) : RecyclerView.Adapter<GalleryRecyclerviewAdapter.ViewHolder>() {

    override fun getItemCount(): Int = photos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.gallery_photo_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curPhoto: GalleryPhoto? = photos[position]
        if (curPhoto?.url != "" && curPhoto?.url != null) {
            val url: String? = curPhoto.url
            val annotation: String? = curPhoto.annotation
            Picasso.with(applicationContext).load(url).into(holder.imageView)
            if (annotation != null && annotation != "")
                holder.imageView?.setOnClickListener {
                    Toast.makeText(
                        applicationContext,
                        annotation, Toast.LENGTH_SHORT
                    ).show()
                }
        } else
            holder.imageView = null

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView? = null

        init {
            imageView = itemView.findViewById(R.id.gallery_photo)
        }
    }

    fun addPhoto(photo: GalleryPhoto) {
        photos.add(photo)
    }
}