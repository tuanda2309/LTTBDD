package com.example.tuan5

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.*
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

    private lateinit var googleButton: Button
    private lateinit var facebookButton: Button
    private lateinit var githubButton: Button
    private lateinit var microsoftButton: Button
    private lateinit var yahooButton: Button

    // --- Google launcher ---
    private val googleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener { signInTask ->
                            if (signInTask.isSuccessful) {
                                goToProfile()
                            } else {
                                Toast.makeText(this, "Google đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                            }
                        }
                } catch (e: ApiException) {
                    Toast.makeText(this, "Lỗi Google API: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        FacebookSdk.sdkInitialize(applicationContext)

        auth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()

        googleButton = findViewById(R.id.google)
        facebookButton = findViewById(R.id.facebook)
        githubButton = findViewById(R.id.github)
        microsoftButton = findViewById(R.id.microsoft)
        yahooButton = findViewById(R.id.yahoo)

        // --- Google ---
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        googleButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleLauncher.launch(signInIntent)
        }

        // --- Facebook ---
        facebookButton.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, listOf("email", "public_profile"))

            LoginManager.getInstance()
                .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult) {
                        handleFacebookAccessToken(result.accessToken)
                    }

                    override fun onCancel() {
                        Toast.makeText(this@MainActivity, "Đăng nhập Facebook bị hủy", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(error: FacebookException) {
                        Toast.makeText(this@MainActivity, "Lỗi: ${error.message}", Toast.LENGTH_LONG).show()
                    }
                })
        }

        githubButton.setOnClickListener { signInWithGitHub() }

        microsoftButton.setOnClickListener { signInWithMicrosoft() }

        yahooButton.setOnClickListener { signInWithYahoo() }
    }

    // --- Facebook ---
    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val request = GraphRequest.newMeRequest(token) { obj, _ ->
                        try {
                            val name = obj?.optString("name")
                            val email = obj?.optString("email")
                            val photoUrl = obj?.getJSONObject("picture")
                                ?.getJSONObject("data")?.optString("url")

                            goToProfile(name, email, photoUrl)
                        } catch (e: JSONException) {
                            goToProfile()
                        }
                    }
                    val parameters = Bundle()
                    parameters.putString("fields", "id,name,email,picture.type(large)")
                    request.parameters = parameters
                    request.executeAsync()
                } else {
                    Toast.makeText(this, "Đăng nhập Facebook thất bại", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // --- GitHub ---
    private fun signInWithGitHub() {
        val provider = OAuthProvider.newBuilder("github.com")

        val pending = auth.pendingAuthResult
        if (pending != null) {
            pending.addOnSuccessListener { handleGenericUser("GitHub") }
                .addOnFailureListener { showError(it, "GitHub") }
        } else {
            auth.startActivityForSignInWithProvider(this, provider.build())
                .addOnSuccessListener { handleGenericUser("GitHub") }
                .addOnFailureListener { showError(it, "GitHub") }
        }
    }

    // --- Microsoft ---
    private fun signInWithMicrosoft() {
        val provider = OAuthProvider.newBuilder("microsoft.com")
        provider.addCustomParameter("prompt", "select_account")
        provider.scopes = listOf("email", "profile", "openid")

        val pending = auth.pendingAuthResult
        if (pending != null) {
            pending.addOnSuccessListener { handleGenericUser("Microsoft") }
                .addOnFailureListener { showError(it, "Microsoft") }
        } else {
            auth.startActivityForSignInWithProvider(this, provider.build())
                .addOnSuccessListener { handleGenericUser("Microsoft") }
                .addOnFailureListener { showError(it, "Microsoft") }
        }
    }

    // --- Yahoo ---
    private fun signInWithYahoo() {
        val provider = OAuthProvider.newBuilder("yahoo.com")
        provider.addCustomParameter("prompt", "login")
        provider.scopes = listOf("email", "profile", "openid")

        val pending = auth.pendingAuthResult
        if (pending != null) {
            pending.addOnSuccessListener { handleGenericUser("Yahoo") }
                .addOnFailureListener { showError(it, "Yahoo") }
        } else {
            auth.startActivityForSignInWithProvider(this, provider.build())
                .addOnSuccessListener { handleGenericUser("Yahoo") }
                .addOnFailureListener { showError(it, "Yahoo") }
        }
    }

    private fun handleGenericUser(providerName: String) {
        val user = auth.currentUser
        val name = user?.displayName ?: "$providerName User"
        val email = user?.email ?: "Không có email"
        val photoUrl = user?.photoUrl?.toString()
        goToProfile(name, email, photoUrl)
    }

    private fun showError(ex: Exception, provider: String) {
        Toast.makeText(this, "$provider: ${ex.message}", Toast.LENGTH_SHORT).show()
    }

    private fun goToProfile(name: String? = null, email: String? = null, photoUrl: String? = null) {
        val user = auth.currentUser
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("name", name ?: user?.displayName)
        intent.putExtra("email", email ?: user?.email)
        intent.putExtra("photoUrl", photoUrl ?: user?.photoUrl?.toString())
        intent.putExtra("date", currentDate)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
