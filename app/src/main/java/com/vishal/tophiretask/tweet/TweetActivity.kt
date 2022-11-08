package com.vishal.tophiretask.tweet

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vishal.tophiretask.MainViewModel
import com.vishal.tophiretask.MainViewModelFactory
import com.vishal.tophiretask.R
import com.vishal.tophiretask.databinding.ActivityTweetBinding
import com.vishal.tophiretask.functions.SharedPref
import com.vishal.tophiretask.model.RequestTweet
import com.vishal.tophiretask.repository.Repository
import com.vishal.tophiretask.utils.Constants

class TweetActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    lateinit var dataSharedPref : SharedPreferences
    lateinit var dataSharedPrefEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTweetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tweetAdapter = TweetAdapter(this)
        binding.tweetRecyclerview.adapter = tweetAdapter

        dataSharedPref = getSharedPreferences(Constants.DATA, Context.MODE_PRIVATE)
        dataSharedPrefEditor = dataSharedPref.edit()

        val userData = SharedPref.getObject(dataSharedPref, Constants.USER_DETAILS)

        binding.signOut.setOnClickListener {
            SharedPref.setObject(dataSharedPrefEditor, Constants.USER_DETAILS,null)
            finish()
        }

        if (userData != null){
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
            viewModel.getTweets(userData.token)

            binding.swipeRefresh.setOnRefreshListener {
                viewModel.getTweets(userData.token)
                binding.swipeRefresh.isRefreshing = false
            }

            viewModel.myResponse4.observe(this, Observer { response ->
                if (response.isSuccessful){
                    response.body()?.let {
                        val list = it.toList().reversed()
                        tweetAdapter.submitList(list)
                    }
                }else{
                    Toast.makeText(this, response.errorBody().toString(), Toast.LENGTH_SHORT).show()
                }
            })

            viewModel.myResponse3.observe(this, Observer { response ->
                if (response.isSuccessful){
                    response.body()?.let {
                        viewModel.getTweets(userData.token)
                    }
                }else{
                    //Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, response.errorBody().toString(), Toast.LENGTH_SHORT).show()
                }
            })

            binding.fab.setOnClickListener {
                showDialog(userData.first_name,userData._id,addTweet = { addTweet ->
                    viewModel.tweet(userData.token,addTweet)
                })
            }
        }
    }

    private fun showDialog( name:String,id:String,addTweet: (RequestTweet) -> Unit) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.tweet_dialog)
        dialog.setCancelable(true)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        val et_post = dialog.findViewById<EditText>(R.id.et_post)
        dialog.findViewById<View>(R.id.bt_cancel)
            .setOnClickListener { v: View? -> dialog.dismiss() }
        val dlgName = dialog.findViewById<View>(R.id.dlg_name) as TextView
        val dlgId = dialog.findViewById<View>(R.id.dlg_id) as TextView
        dlgId.text = id
        dlgName.text = name
        dialog.findViewById<View>(R.id.bt_submit).setOnClickListener { v: View? ->
            val review = et_post.text.toString().trim { it <= ' ' }
            if (review.isEmpty()) {
                Toast.makeText(this, "Please add a tweet", Toast.LENGTH_SHORT).show()
            } else {
                val tweet = RequestTweet(review)
                addTweet(tweet)
                dialog.dismiss()
            }
        }
        dialog.show()
        dialog.window!!.attributes = lp
    }
}