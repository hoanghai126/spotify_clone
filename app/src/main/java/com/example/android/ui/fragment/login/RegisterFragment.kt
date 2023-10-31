package com.example.android.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.android.R
import com.example.android.databinding.FragmentRegisterBinding
import com.example.android.ui.fragment.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class RegisterFragment : BaseFragment(), HasAndroidInjector {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val binding: FragmentRegisterBinding by lazy {
        FragmentRegisterBinding.inflate(layoutInflater)
    }

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.tvRegisterInRegisterLayout.setOnClickListener {
            val email = binding.usernameMailInputEdt.text.toString()
            val password = binding.passwordInputEdt.text.toString()
            val rePassword = binding.passwordRepeatInputEdt.text.toString()
            val userName = binding.nameInputEdt.text.toString()
            if (isEmailValid(email) && password == rePassword) {
                registerUser(email, password, userName)
            }
        }

        binding.tvLogin2.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun registerUser(email: String, password: String, userName: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                saveUserToFirestore(email, password, userName)

                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(
                    requireContext(), "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    private fun saveUserToFirestore(email: String, password: String, userName: String) {
        val collection = db?.collection("users")
        val user = hashMapOf(
            "email" to email,
            "password" to password,
            "username" to userName
        )
        collection?.add(user)
    }

    fun isEmailValid(email: String): Boolean {
        val emailPattern = Regex(pattern = "[a-zA-Z\\d._-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,4}")
        return emailPattern.matches(email)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}