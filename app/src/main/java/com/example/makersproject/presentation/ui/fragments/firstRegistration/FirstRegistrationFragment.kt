package com.example.makersproject.presentation.ui.fragments.firstRegistration

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
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

        initGoogle()

        binding.btnGmail.setOnClickListener {
            googleSignUp()
        }

        binding.btnRegistration.setOnClickListener {
            signUpWithEmailAndPassword()
        }
    }

    private fun signUpWithEmailAndPassword() {
            App.fbAuth.createUserWithEmailAndPassword(
                binding.etEmail.text.toString(), binding.etPassword.text.toString())
                .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sendEmailVerification(task.result?.user!!)
                    close()
                }
                else {
                    Toast.makeText(context, getString(R.string.cannot_registrated_rus), Toast.LENGTH_SHORT).show()
                }
            }
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

    private fun authWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        App.fbAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = App.fbAuth.currentUser
                    updateUI(user)
                    close()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "AuthError ", Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback {
            if (it.resultCode == RESULT_OK) {
                try {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                    val account = task.getResult(ApiException::class.java)!!
                    authWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            }
        })

    // Check if user is signed in (non-null) and update UI accordingly.
    override fun onStart() {
        super.onStart()
        val currentUser = App.fbAuth.currentUser
        updateUI(currentUser)
    }

    // disable/enable buttons or set visibility
    private fun updateUI(user: FirebaseUser?) {

    }

    private fun close() {
        val navController = findNavController()
        navController.navigate(R.id.mainFragment)
    }

}