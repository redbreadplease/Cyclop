package com.redbreadplease.cyclop.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.redbreadplease.cyclop.R
import com.redbreadplease.cyclop.retrofit.pojos.NewsPost
import com.squareup.picasso.Picasso


class RecyclerViewAdapter(
    private val posts: MutableList<NewsPost?>,
    private val UIContext: Context
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount(): Int = posts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (posts[position]?.title != "" && posts[position]?.title != null)
            holder.titleView?.text = posts[position]?.title!!.replace(".", "")

        if (posts[position]?.annotation != "" && posts[position]?.annotation != null)
            holder.annotationView?.text = posts[position]?.annotation
        else {
            holder.annotationView?.text = posts[position]?.body!!.split('.')[0].plus(".")
            posts[position]?.body = posts[position]?.body!!.split(".")
                .filterIndexed { i, _ -> i > 0 }.joinToString(".").plus(".")
        }

        if (posts[position]?.photo_urls != "" && posts[position]?.photo_urls != null) {
            val url = posts[position]?.photo_urls?.split("||")?.get(0)
            Picasso.with(UIContext).load(url).into(holder.previewImageView)
        } else
            holder.previewImageView = null

        holder.showPostBody?.setOnClickListener {
            holder.bodyView!!.visibility = View.VISIBLE
            holder.bodyView!!.text = posts[position]!!.body
            it.setClickable(false)
            it.visibility = View.INVISIBLE
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleView: TextView? = null
        var annotationView: TextView? = null
        var bodyView: TextView? = null
        var previewImageView: ImageView? = null
        var showPostBody: Button? = null

        init {
            titleView = itemView.findViewById(R.id.post_title)
            annotationView = itemView.findViewById(R.id.post_annotation)
            bodyView = itemView.findViewById(R.id.post_body)
            previewImageView = itemView.findViewById(R.id.post_preview_img)
            showPostBody = itemView.findViewById(R.id.button_show_post_body)
        }
    }

    fun addPost(post: NewsPost) {
        posts.add(post)
    }
}