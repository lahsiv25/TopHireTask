package com.vishal.tophiretask.onboarding.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.vishal.tophiretask.MainViewModel
import com.vishal.tophiretask.R
import com.vishal.tophiretask.databinding.FragmentLoginBinding
import com.vishal.tophiretask.functions.SharedPref
import com.vishal.tophiretask.model.RequestLogin
import com.vishal.tophiretask.tweet.TweetActivity
import com.vishal.tophiretask.utils.Constants

class LoginFragment : Fragment() {

    var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewmodel:MainViewModel by activityViewModels()
    lateinit var dataSharedPref : SharedPreferences
    lateinit var dataSharedPrefEditor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        dataSharedPref = requireActivity().getSharedPreferences(Constants.DATA, Context.MODE_PRIVATE)
        dataSharedPrefEditor = dataSharedPref.edit()


        binding.fragLoginRegisterTv.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            it.findNavController().navigate(action)
        }

        binding.loginEmailEt.addTextChangedListener { text ->
            viewmodel.isEmailValid(text.toString(),viewmodel.isLoginEmailValidLiveData)
        }
        viewmodel.isLoginEmailValidLiveData.observe(viewLifecycleOwner, Observer { EmailValid ->
            if (!EmailValid){
                binding.fragLoginEmailEt.error = getString(R.string.email_error)
            }else binding.fragLoginEmailEt.error = null

        })
        binding.loginPwEt.addTextChangedListener { text ->
            viewmodel.isPasswordValid(text.toString(),viewmodel.isLoginPwValidLiveData)
        }
        viewmodel.isLoginPwValidLiveData.observe(viewLifecycleOwner, Observer { isPwValid ->
            if (!isPwValid){
                binding.fragLoginPwEt.error = getString(R.string.password_error)
                binding.fragLoginPwEt.errorIconDrawable = null
            }else binding.fragLoginPwEt.error = null
        })

        binding.fragLoginLoginBt.setOnClickListener {
            if(!binding.loginEmailEt.text.isNullOrBlank()){
                if (!binding.loginPwEt.text.isNullOrBlank()){
                    if (binding.fragLoginPwEt.error == null && binding.fragLoginEmailEt.error == null){
                        val credentials = RequestLogin(binding.loginEmailEt.text.toString().trim(),binding.loginPwEt.text.toString().trim())
                        it.isClickable = false
                        viewmodel.loginUser(credentials)
                    }
                }else binding.fragLoginPwEt.error = getString(R.string.password_error)
            }else binding.fragLoginEmailEt.error = getString(R.string.email_error)
        }

        viewmodel.myResponse2.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful){
                binding.fragLoginLoginBt.isClickable = true
                response.body()?.let {
                    SharedPref.setObject(dataSharedPrefEditor, Constants.USER_DETAILS,it)
                    val intent = Intent(activity,TweetActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }else{
                binding.fragLoginLoginBt.isClickable = true
                Toast.makeText(requireContext(), "Wrong credentials", Toast.LENGTH_SHORT).show()
            }
        })

    }
}