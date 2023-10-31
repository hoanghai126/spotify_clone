package com.example.android.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.android.databinding.GetStartedFragmentBinding
import dagger.android.support.DaggerFragment

class GetStartedFragment : DaggerFragment() {
    private lateinit var binding: GetStartedFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GetStartedFragmentBinding.inflate(inflater, container, false)
        binding.btnGetStarted.setOnClickListener {
            val action = GetStartedFragmentDirections.actionGetStartedToSignUpFragment()
            Navigation.findNavController(it).navigate(action)
        }
        return binding.root
    }

}