package com.example.makersproject.presentation.ui.fragments.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.makersproject.R
import com.example.makersproject.data.models.Profile
import com.example.makersproject.databinding.FragmentEditAccountBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EditAccountFragment : Fragment() {

    private lateinit var binding: FragmentEditAccountBinding
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveData.setOnClickListener {
            database = Firebase.database("https://makersalex-ee99d-default-rtdb.asia-southeast1.firebasedatabase.app/").reference
            writeNewUser(
                binding.etAddName.toString(), binding.etAddSpeciality.toString(),
                binding.etAddCity.toString(), binding.etAddContact.toString(),
                binding.etAddEducation.toString(), binding.etAddExperience.toString()
            )
            findNavController().navigate(R.id.accountFragment)
        }

    }

    private fun writeNewUser(
        name: String?, speciality: String?, city: String?, contacts: String?,
        education: String?, experience: String?
    ) {
        val user = Profile(null, name, speciality, city, contacts, education, experience)

        if (name.toString().isNotEmpty()) {
            if (name != null) {
                database.child(name).setValue(name)
            }
        } else {
            binding.etAddName.error = getString(R.string.et_add_name_error_rus)
        }
    }
}