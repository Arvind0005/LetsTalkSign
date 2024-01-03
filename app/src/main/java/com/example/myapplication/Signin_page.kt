package com.example.myapplication
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class Signin_page : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123
    private var progressDialog: ProgressDialog? = null
    private lateinit var firebaseAuth: FirebaseAuth

    private val serviceIntent by lazy { Intent(this, FloatingWindowGFG::class.java) }

//    override fun onResume() {
//        super.onResume()
//        startService(serviceIntent)
//    }

    override fun onPause() {
        super.onPause()
        stopService(serviceIntent)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_page)
        progressDialog = ProgressDialog(this)
        var Signin=findViewById<ImageView>(R.id.Signin);
        val receivedIntent = intent
        var sharedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT)
        if (sharedText != null) {
            val intent4 = Intent(this@Signin_page, FloatingWindowGFG::class.java);
            intent4.putExtra("url", sharedText.toString());
            startService(intent4);
            finish();
        };

        FirebaseApp.initializeApp(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("238702597146-4f34ai7mlr0n81r85rn3ih0mpi4f79a2.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()

        Signin.setOnClickListener { view: View? ->
            Toast.makeText(this, "Logging In", Toast.LENGTH_SHORT).show()
            signInGoogle()
        }
    }
    fun onAppPause() {
        Toast.makeText(applicationContext, "App in background", Toast.LENGTH_LONG).show()

    }
    private fun signInGoogle() {
        progressDialog?.setMessage("Logging In...")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    // onActivityResult() function : this is where
    // we provide the task and data for the Google Account
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            println("errrrrrrrrrrrrrrrrrror");
            println(e.toString());
//            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    // this is where we update the UI after Google signin takes place
    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            progressDialog?.dismiss()
            if (task.isSuccessful) {

                val user = firebaseAuth.currentUser
                val profilePicUri = user?.photoUrl

                // Now you have the profile picture URI, you can use it as needed
                if (profilePicUri != null) {
                    println("yeeeeeeeeeeeeeeeeeeeeeeeeeeeeeessshelo");
                    println(profilePicUri);
                    saveProfilePicUri(profilePicUri.toString())
                    // Use a library like Picasso or Glide to load and display the image
                    // Example using Picasso (make sure to add the Picasso dependency to your app)
                    // Picasso.get().load(profilePicUri).into(imageViewProfilePicture)

                    // Example using Glide (make sure to add the Glide dependency to your app)
                    // Glide.with(this).load(profilePicUri).into(imageViewProfilePicture)
                }
//                SavedPreference.setEmail(this, account.email.toString())
//                SavedPreference.setUsername(this, account.displayName.toString())
                println("Noooooooooooooooooooooooooooooooooooooooolooooooo");
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("profilepic",profilePicUri.toString());
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"unexpected error occurred please try again",Toast.LENGTH_LONG).show();
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            startActivity(
                Intent(
                    this, MainActivity
                    ::class.java
                )
            )
            finish()
        }
    }
    private fun saveProfilePicUri(uri: String) {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("PROFILE_PIC_URI", uri)
        editor.apply()
    }
}
