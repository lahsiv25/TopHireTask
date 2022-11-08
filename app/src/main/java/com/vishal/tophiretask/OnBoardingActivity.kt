package com.vishal.tophiretask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vishal.tophiretask.model.RequestLogin
import com.vishal.tophiretask.model.RequestNewUser
import com.vishal.tophiretask.model.RequestTweet
import com.vishal.tophiretask.repository.Repository

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)

        //viewModel.updateTweet("636a4fee08fe51100d626351",tweet)
        /*viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful){
                response.body()?.let {
                    Log.d("welcome Message", it.message)
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, response.errorBody().toString(), Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.myResponse2.observe(this, Observer { response ->
            if (response.isSuccessful){
                response.body()?.let {
                    Toast.makeText(this, it._id, Toast.LENGTH_SHORT).show()
                }
            }else{
                //Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, response.errorBody().toString(), Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.myResponse2.observe(this, Observer { response ->
            if (response.isSuccessful){
                response.body()?.let {
                    Toast.makeText(this, it.token, Toast.LENGTH_SHORT).show()
                }
            }else{
                //Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, response.errorBody().toString(), Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.myResponse4.observe(this, Observer { response ->
            if (response.isSuccessful){
                response.body()?.let {
                    Toast.makeText(this, it[0].tweet, Toast.LENGTH_SHORT).show()
                }
            }else{
                //Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, response.errorBody().toString(), Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.myResponse5.observe(this, Observer { response ->
            if (response.isSuccessful){
                response.body()?.let {
                    Toast.makeText(this, it.tweet, Toast.LENGTH_SHORT).show()
                    println(it.tweet)
                }
            }else{
                //Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, response.errorBody().toString(), Toast.LENGTH_SHORT).show()
            }
        })*/

    }
}