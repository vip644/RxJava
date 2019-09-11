package com.vipin.flatmap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vipin.flatmap.model.Post
import kotlinx.android.synthetic.main.item_view_layout.view.*

/**
 * Created by vipin.c on 11/09/2019
 */
class DataCommentAdapter : RecyclerView.Adapter<DataCommentAdapter.ViewHolder>() {

    private var mPost: MutableList<Post> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mPost.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mPost[position])
    }

    fun setPosts(post: MutableList<Post>){
        this.mPost = post
        notifyDataSetChanged()
    }

    fun updatePost(post: Post){
        mPost[mPost.indexOf(post)] = post
        notifyItemChanged(mPost.indexOf(post))

    }

    fun getPosts() : List<Post>{
        return mPost
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(post: Post) {

            itemView.title.text = post.title
            if (post.comments == null) {
                showProgress(true)
                itemView.num_comments.text = ""
            }else {
                showProgress(false)
                itemView.num_comments.text = post.comments?.size.toString()
            }
        }

        fun showProgress(showProgressBar: Boolean) {
            if (showProgressBar) {
                itemView.progress_bar.visibility = View.VISIBLE
            } else {
                itemView.progress_bar.visibility = View.GONE
            }
        }

    }
}