package com.vishal.tophiretask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishal.tophiretask.model.*
import com.vishal.tophiretask.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository):ViewModel() {

    val myResponse:MutableLiveData<Response<Welcome>> = MutableLiveData()
    val myResponse2:MutableLiveData<Response<NewUser>> = MutableLiveData()
    val myResponse3:MutableLiveData<Response<Tweet>> = MutableLiveData()
    val myResponse4:MutableLiveData<Response<ArrayList<Tweet>>> = MutableLiveData()
    val myResponse5:MutableLiveData<Response<RequestTweet>> = MutableLiveData()

    fun updateTweet(id:String,tweet:RequestTweet){
        viewModelScope.launch {
            val response:Response<RequestTweet> = repository.updateTweet(id,tweet)
            myResponse5.value = response
        }
    }

    fun getTweets(key:String){
        viewModelScope.launch {
            val response:Response<ArrayList<Tweet>> = repository.getTweets(key)
            myResponse4.value = response
        }
    }

    fun tweet(key:String,tweet:RequestTweet){
        viewModelScope.launch {
            val response:Response<Tweet> = repository.tweet(key,tweet)
            myResponse3.value = response
        }
    }

    fun loginUser(credentials: RequestLogin){
        viewModelScope.launch {
            val response:Response<NewUser> = repository.loginUser(credentials)
            myResponse2.value = response
        }
    }

    fun registerNewUser(userDetails:RequestNewUser){
        viewModelScope.launch {
            val response:Response<NewUser> = repository.registerNewUser(userDetails)
            myResponse2.value = response
        }
    }

    fun getWelcomeMessage(key:String){
        viewModelScope.launch {
            val response:Response<Welcome> = repository.getWelcomeMessage(key)
            myResponse.value = response
        }
    }

    fun isEmailValid(text:String,liveDataEm: MutableLiveData<Boolean>){
        val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
        liveDataEm.value = EMAIL_REGEX.toRegex().matches(text) && text.isNotEmpty()
    }

    fun isPasswordValid(text:String,liveDataPw: MutableLiveData<Boolean>){
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        //PASSWORD_PATTERN.toRegex().matches(text) &&
        liveDataPw.value =  text.length >= 6
    }

    fun isNameValid(text: String,liveDataName:MutableLiveData<Boolean>){
        liveDataName.value = text.length >= 3
    }


    // login fragment
    var isLoginEmailValidLiveData:MutableLiveData<Boolean> = MutableLiveData(true)
    var isLoginPwValidLiveData:MutableLiveData<Boolean> = MutableLiveData(true)

    // register fragment
    var isRegisterFirstNameValidLiveData:MutableLiveData<Boolean> = MutableLiveData(true)
    var isRegisterLastNameValidLiveData:MutableLiveData<Boolean> = MutableLiveData(true)
    var isRegisterEmailValidLiveData:MutableLiveData<Boolean> = MutableLiveData(true)
    var isRegisterPwValidLiveData:MutableLiveData<Boolean> = MutableLiveData(true)

    // forget password fragment
    var isForpwEmailValidLiveData:MutableLiveData<Boolean> = MutableLiveData(true)

    // set password fragment
    var isSetpwPwValidLiveData:MutableLiveData<Boolean> = MutableLiveData(true)
    var currentNewPassword:String? = null
    var isSetpwCpwValidLiveData:MutableLiveData<Boolean> = MutableLiveData(true)
    fun isPasswordSame(repPw:String,enteredPw:String?,liveDataPw: MutableLiveData<Boolean>){
        liveDataPw.value = repPw == enteredPw
    }

}