package com.vishal.tophiretask.onboarding.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.vishal.tophiretask.MainViewModel
import com.vishal.tophiretask.R
import com.vishal.tophiretask.databinding.FragmentLoginBinding
import com.vishal.tophiretask.databinding.FragmentRegisterBinding
import com.vishal.tophiretask.functions.SharedPref
import com.vishal.tophiretask.model.NewUser
import com.vishal.tophiretask.model.RequestNewUser
import com.vishal.tophiretask.tweet.TweetActivity
import com.vishal.tophiretask.utils.Constants.Companion.DATA
import com.vishal.tophiretask.utils.Constants.Companion.USER_DETAILS

class RegisterFragment : Fragment() {

    private var _binding:FragmentRegisterBinding? = null
    private val binding get()= _binding!!
    private val viewmodel: MainViewModel by activityViewModels()
    lateinit var dataSharedPref :SharedPreferences
    lateinit var dataSharedPrefEditor: SharedPreferences.Editor


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataSharedPref = requireActivity().getSharedPreferences(DATA, Context.MODE_PRIVATE)
        dataSharedPrefEditor = dataSharedPref.edit()


        binding.fragRegLogTv.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.regFirnamEt.addTextChangedListener { text ->
            viewmodel.isNameValid(text.toString(),viewmodel.isRegisterFirstNameValidLiveData)
        }
        viewmodel.isRegisterFirstNameValidLiveData.observe(viewLifecycleOwner, Observer { isNameValid ->
            if (!isNameValid){
                binding.fragRegFirnameEt.error = getString(R.string.name_error)
            }else binding.fragRegFirnameEt.error = null
        })

        binding.regLastnamEt.addTextChangedListener { text ->
            viewmodel.isNameValid(text.toString(),viewmodel.isRegisterLastNameValidLiveData)
        }
        viewmodel.isRegisterLastNameValidLiveData.observe(viewLifecycleOwner, Observer { isNameValid ->
            if (!isNameValid){
                binding.fragRegLasnameEt.error = getString(R.string.name_error)
            }else binding.fragRegLasnameEt.error = null
        })

        binding.regEmailEt.addTextChangedListener { text ->
            viewmodel.isEmailValid(text.toString(),viewmodel.isRegisterEmailValidLiveData)
        }
        viewmodel.isRegisterEmailValidLiveData.observe(viewLifecycleOwner, Observer { isEmailValid ->
            if (!isEmailValid){
                binding.fragRegEmailEt.error = getString(R.string.email_error)
            }else binding.fragRegEmailEt.error = null
        })

        binding.regPwEt.addTextChangedListener { text ->
            viewmodel.isPasswordValid(text.toString(),viewmodel.isRegisterPwValidLiveData)
        }
        viewmodel.isRegisterPwValidLiveData.observe(viewLifecycleOwner, Observer { isPwValid ->
            if (!isPwValid){
                binding.fragRegPwEt.error = getString(R.string.password_error)
                binding.fragRegPwEt.errorIconDrawable = null
            }else binding.fragRegPwEt.error = null
        })
        fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)


        binding.fragRegRegisterBt.setOnClickListener {
            if (!binding.regFirnamEt.text.isNullOrBlank()){
                if(!binding.regLastnamEt.text.isNullOrBlank()){
                    if (!binding.regEmailEt.text.isNullOrBlank()){
                        if (!binding.regPwEt.text.isNullOrBlank()){
                            if (binding.fragRegPwEt.error == null &&
                                binding.fragRegEmailEt.error == null &&
                                binding.fragRegLasnameEt.error == null &&
                                binding.fragRegFirnameEt.error == null ){
                                val newUser = RequestNewUser(
                                    binding.regFirnamEt.text.toString().trim(),
                                    binding.regLastnamEt.text.toString().trim(),
                                    binding.regEmailEt.text.toString().trim(),
                                    binding.regPwEt.text.toString().trim()
                                )
                                it.isClickable = false
                                viewmodel.registerNewUser(newUser)
                            }
                        }else{
                            binding.fragRegPwEt.error = getString(R.string.password_error)
                        }
                    }else{
                        binding.fragRegEmailEt.error = getString(R.string.email_error)
                    }
                }else{
                    binding.fragRegLasnameEt.error = getString(R.string.name_error)
                }
            }else{
                binding.fragRegFirnameEt.error = getString(R.string.name_error)
            }
        }

        viewmodel.myResponse2.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful){
                binding.fragRegRegisterBt.isClickable = true
                response.body()?.let {
                    SharedPref.setObject(dataSharedPrefEditor,USER_DETAILS,it)
                    val intent = Intent(activity,TweetActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }else{
                binding.fragRegRegisterBt.isClickable = true
                Toast.makeText(requireContext(), "user already found / network error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}