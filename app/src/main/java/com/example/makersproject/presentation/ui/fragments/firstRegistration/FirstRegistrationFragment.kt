package com.example.makersproject.presentation.ui.fragments.firstRegistration

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.makersproject.App
import com.example.makersproject.R
import com.example.makersproject.databinding.FragmentFirstRegistrationBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class FirstRegistrationFragment : Fragment(R.layout.fragment_first_registration) {

    private val binding: FragmentFirstRegistrationBinding by viewBinding()
    private lateinit var googleClient: GoogleSignInClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        initGoogle()



        binding.btnGmail.setOnClickListener {
            googleSignUp()
        }

        binding.btnRegistration.setOnClickListener {
            signUpWithEmailAndPassword(email, password)
        }
    }

    private fun signUpWithEmailAndPassword(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            App.fbAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sendEmailVerification(task.result?.user!!)
                        close()
                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.cannot_registrated_rus),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
    }

    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    context,
                    getString(R.string.send_email_verification_rus),
                    Toast.LENGTH_SHORT
                ).show()
            } else Toast.makeText(
                context,
                getString(R.string.cannot_send_email_verification_rus),
                Toast.LENGTH_SHORT
            ).show()
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

    private fun googleSignUp() {
        val intent = googleClient.signInIntent
        resultLauncher.launch(intent)
    }

    private fun authWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(OnCompleteListener<AuthResult>() { task ->
                if (task.isSuccessful) {
                    close()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "AuthError ", Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun close() {
        val navController = findNavController()
        navController.navigateUp()

    }

    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback {
            if (it.resultCode == RESULT_OK) {
                try {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(it.data)
                    val account = task.getResult(ApiException::class.java)!!
                    authWithGoogle(account)
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            }
        })


}