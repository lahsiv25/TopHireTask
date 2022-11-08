package com.vishal.tophiretask.tweet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vishal.tophiretask.R
import com.vishal.tophiretask.model.Tweet

class TweetAdapter(val context: Context):
    ListAdapter<Tweet, TweetAdapter.ViewHolder>(CartDiffUtil()) {

    //private val viewmodel: LoginActivityViewmodel by activityViewModels()


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val id = view.findViewById<TextView>(R.id.user_id)
        val version = view.findViewById<TextView>(R.id.version)
        val tweetTv = view.findViewById<TextView>(R.id.tweet)

        fun update(tweet:Tweet){
            id.text = tweet._id
            version.text = tweet.__v
            tweetTv.text = tweet.tweet
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tweet_item_view,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.update(getItem(position))
        //holder.productDelete.setOnClickListener { CartProductsAdapter }

    }


    class CartDiffUtil: DiffUtil.ItemCallback<Tweet>(){
        override fun areItemsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
            return newItem.toString() == oldItem.toString()
        }

        override fun areContentsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
            return areItemsTheSame(oldItem,newItem)
        }

    }
}