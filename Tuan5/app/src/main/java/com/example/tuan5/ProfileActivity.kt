package com.example.tuan5

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var avatarImage: ImageView
    private lateinit var cameraFrame: FrameLayout
    private lateinit var nameField: EditText
    private lateinit var emailField: EditText
    private lateinit var dateField: EditText
    private lateinit var signOutButton: Button
    private lateinit var backArrow: ImageView

    private var selectedImageUri: Uri? = null

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    Glide.with(this).load(it).into(avatarImage)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        avatarImage = findViewById(R.id.avatarImage)
        cameraFrame = findViewById(R.id.avatarFrame)
        nameField = findViewById(R.id.name)
        emailField = findViewById(R.id.email)
        dateField = findViewById(R.id.date)
        signOutButton = findViewById(R.id.google)
        backArrow = findViewById(R.id.imageView3)

        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val photoUrl = intent.getStringExtra("photoUrl")
        val date = intent.getStringExtra("date")

        nameField.setText(name ?: "Không có tên")
        emailField.setText(email ?: "Không có email")
        dateField.setText(date ?: "")

        emailField.isEnabled = false
        dateField.isEnabled = false

        if (!photoUrl.isNullOrEmpty()) {
            Glide.with(this).load(photoUrl).into(avatarImage)
        }

        cameraFrame.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickImageLauncher.launch(intent)
        }

        val auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, gso)

        signOutButton.setOnClickListener {
            auth.signOut()
            googleClient.signOut()
            LoginManager.getInstance().logOut()

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }


        backArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
