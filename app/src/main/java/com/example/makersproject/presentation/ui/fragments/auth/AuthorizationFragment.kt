package com.example.makersproject.presentation.ui.fragments.auth

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.makersproject.R
import com.example.makersproject.databinding.FragmentAuthorizationBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.GoogleAuthProvider


class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {
    private val binding: FragmentAuthorizationBinding by viewBinding()
    private lateinit var googleClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGmail()
        initEditText()
        resetPassword()

        binding.tvRegistrationSignIn.setOnClickListener {
            findNavController().navigate(R.id.firstRegistrationFragment)
        }
        binding.btnGmailInSign.setOnClickListener {
            gmailSignIn()
        }
    }

    private fun resetPassword() {
        binding.tvRecover.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Forgot password")
            val view = layoutInflater.inflate(R.layout.dialog_forget_password, null)
            val username = view.findViewById<EditText>(R.id.etEmail)
            builder.setView(view)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, _ ->
                forgetPassword(username)
            })
            builder.setNegativeButton("close", DialogInterface.OnClickListener { _, _ -> })
            builder.show()
        }
    }

    private fun forgetPassword(username: EditText) {
        if (username.text.toString().isEmpty())
        {
            Toast.makeText(requireContext(),
                "Fields cannot be empty ", Toast.LENGTH_SHORT).show()
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            return
        }
        else {
            auth.sendPasswordResetEmail(username.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context,
                            "Password reset link sent "+task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            task.exception!!.message.toString(),
                            Toast.LENGTH_LONG).show()

                    }
                }
        }
    }

    private fun initEditText() {
        binding.btnSignIn.setOnClickListener {
            if (binding.etMailSignIn.text.toString().isEmpty() || binding.etPasswordSignIn.text
                    .toString().isEmpty()
            ) {
                Toast.makeText(requireContext(),
                    "Fields cannot be empty ", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(binding.etMailSignIn.text.toString(), binding
                    .etPasswordSignIn.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        findNavController().navigate(R.id.mainFragment)
                    } else {
                        Toast.makeText(requireContext(),
                            "You have some errors ", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun initGmail() {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client))
            .requestEmail()
            .build()
        googleClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun gmailSignIn() {
        val intent = googleClient.signInIntent
        resultLauncher.launch(intent)
    }

    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(it.data)
            if (it.resultCode == RESULT_OK) {
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    signWithGmail(account)
                } catch (e: ApiException) {
                    e.printStackTrace()

                }
            }
        })

    private fun signWithGmail(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(OnCompleteListener<AuthResult>() { task ->
                if (task.isSuccessful) {
                    Log.d(TAG,"Google signIn done")
                    close()
                    findNavController().navigate(R.id.mainFragment)
                } else {
                    Log.e(TAG,"Google signIn error" + task.exception?.message.toString())

                    Toast.makeText(requireContext(),
                        "Error ", Toast.LENGTH_SHORT).show()
                }
            })
    }
    private fun close() {
        val navController = findNavController()
        navController.navigate(R.id.mainFragment)
    }
}
