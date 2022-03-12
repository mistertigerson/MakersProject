package com.example.makersproject.presentation.ui.fragments.first_registration_fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.makersproject.R
import com.example.makersproject.databinding.FragmentFirstRegistrationBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstRegistrationFragment : Fragment() {

    private val binding: FragmentFirstRegistrationBinding by viewBinding()
    private lateinit var googleClient: GoogleSignInClient
    private val auth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGoogle()

        binding.btnGmail.setOnClickListener {
            googleSignIn()
        }

        binding.btnRegistration.setOnClickListener {
            signInWithEmailAndPassword()
        }
    }

    private fun signInWithEmailAndPassword() {
        auth.createUserWithEmailAndPassword(
            binding.etEmail.toString(),
            binding.etPassword.toString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
            else Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initGoogle() {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client))
            .requestEmail()
            .build()
        googleClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun googleSignIn() {
        val intent = googleClient.signInIntent
        resultLauncher.launch(intent)
    }

    private fun authWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(OnCompleteListener<AuthResult>() {task ->
                if (task.isSuccessful){
                    close()
                } else {
                    Toast.makeText(requireContext(),
                        "AuthError ", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun close() {
        findNavController().navigateUp()
    }

    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback {
            if (it.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)

                try {
                    val account = task.getResult(ApiException::class.java)!!
                    authWithGoogle(account)
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            }
        })

}