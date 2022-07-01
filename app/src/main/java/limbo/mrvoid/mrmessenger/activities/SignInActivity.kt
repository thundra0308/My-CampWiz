package limbo.mrvoid.mrmessenger.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import limbo.mrvoid.mrmessenger.R
import limbo.mrvoid.mrmessenger.databinding.ActivitySignInBinding
import limbo.mrvoid.mrmessenger.firebase.FirestoreClass
import limbo.mrvoid.mrmessenger.models.TeacherUser

class SignInActivity : BaseActivity() {

    private var binding: ActivitySignInBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarsingin)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back0)
        binding?.toolbarsingin?.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.btnSigninsignin?.setOnClickListener {
            signInRegisteredUser()
        }
    }

    private fun signInRegisteredUser() {
        val email: String = binding?.etSigninemail?.text.toString()
        val password: String = binding?.etSigninpassword?.text.toString()
        if(validateForm(email,password)) {
            showProgressDialog("Signing In ....")
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(this@SignInActivity) {
                    task->
                hideProgressDialog()
                if(task.isSuccessful) {
                    FirestoreClass().loadUserData(this)
                } else {
                    Toast.makeText(this,task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please Enter an Email")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please Enter a Password")
                false
            } else -> {
                true
            }
        }
    }

    fun signInSuccess(teacherUser: TeacherUser) {
        hideProgressDialog()
        startActivity(Intent(this@SignInActivity,MainActivity::class.java))
        finish()
    }

}