package com.redbreadplease.cyclop.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.retrofit.pojos.SpaceNewsPost
import com.squareup.picasso.Picasso
import java.security.AccessController.getContext


class RecyclerViewAdapter(
    private val posts: MutableList<SpaceNewsPost?>,
    private val applicationContext: Context
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount(): Int = posts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (posts[position]?.title != "" && posts[position]?.title != null)
            holder.titleView?.text = posts[position]?.title!!.replace(".", "")

        if (posts[position]?.annotation != "" && posts[position]?.annotation != null)
            holder.annotationView?.text = posts[position]?.annotation
        else
            holder.annotationView?.text = posts[position]?.body!!.split('.')[0] + "."

        if (posts[position]?.photo_urls != "" && posts[position]?.photo_urls != null) {
            val url = posts[position]?.photo_urls?.split("||")?.get(0)
            Picasso.with(applicationContext).load(url).into(holder.previewImageView)
        } else
            holder.previewImageView = null

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleView: TextView? = null
        var annotationView: TextView? = null
        // var bodyView: TextView? = null
        var previewImageView: ImageView? = null

        init {
            titleView = itemView.findViewById(R.id.post_title)
            annotationView = itemView.findViewById(R.id.post_annotation)
            previewImageView = itemView.findViewById(R.id.post_preview_img)
        }
    }

    fun addPost(post: SpaceNewsPost) {
        posts.add(post)
    }
}