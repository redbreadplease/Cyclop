package com.example.spacenews.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spacenews.R
import com.example.spacenews.retrofit.pojos.SpaceNewsPost


class RecyclerViewAdapter(
    private val posts: MutableList<SpaceNewsPost?>
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
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleView: TextView? = null
        var annotationView: TextView? = null
        // var bodyView: TextView? = null
        // var previewImageView: ImageView? = null

        init {
            titleView = itemView.findViewById(R.id.post_title)
            annotationView = itemView.findViewById(R.id.post_annotation)
        }
    }

    fun addPost(post: SpaceNewsPost) {
        posts.add(post)
    }
}