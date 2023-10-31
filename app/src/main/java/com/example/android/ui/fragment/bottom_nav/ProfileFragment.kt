package com.example.android.ui.bottom_nav_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.databinding.FragmentProfileBinding
import com.example.android.model.Artist
import com.example.android.ui.fragment.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class ProfileFragment : BaseFragment(), HasAndroidInjector {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        displayListMostPlayedSong()
        loadUserFromFirestore()

        return binding.root
    }

    private fun displayListMostPlayedSong() {
        val layout = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewMps.layoutManager = layout
//        val adapter = SongAdapter()
//        adapter.submitList(getListMPS())
//        profileBinding.recyclerViewMps.adapter = adapter
    }

    private fun loadUserFromFirestore() {
        val user = FirebaseAuth.getInstance().currentUser
        val emailUser = user?.email
        val collection = db.collection("users")
        collection.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val username = document.data["username"].toString()
                    val email = document.data["email"].toString()
                    if (emailUser == email){
                        binding.tvProfileName.text = username
                        binding.tvProfileMail.text = email
                    }

                }
            }
            .addOnFailureListener {

            }
    }
    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}







