package limbo.mrvoid.mrmessenger.activities

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import limbo.mrvoid.mrmessenger.R
import limbo.mrvoid.mrmessenger.databinding.ActivitySignUpBinding
import limbo.mrvoid.mrmessenger.firebase.FirestoreClass
import limbo.mrvoid.mrmessenger.models.TeacherUser

class SignUpActivity : BaseActivity() {
    private var binding: ActivitySignUpBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarsingup)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back0)
        binding?.toolbarsingup?.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.btnSignupsignup?.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val name: String = binding?.etSignupname?.text.toString().trim{it<=' '}
        val email: String = binding?.etSignupemail?.text.toString().trim{it<=' '}
        val password: String = binding?.etSignuppassword?.text.toString().trim{it<=' '}
        if(validateForm(name,email,password)) {
            showProgressDialog("Registering ....")
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(this@SignUpActivity) {
                    task->
                if(task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!
                    val teacherUser = TeacherUser(firebaseUser.uid,name,registeredEmail)
                    FirestoreClass().registerUser(this,teacherUser)
                    userRegisteredSuccess()
                } else {
                    Toast.makeText(this,"Registration Failed !!".toString(),Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please Enter a Name")
                false
            }
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

    fun userRegisteredSuccess() {
        Toast.makeText(this,"You Have Been Successfully Registered", Toast.LENGTH_LONG).show()
        hideProgressDialog()
        FirebaseAuth.getInstance().signOut()
        finish()
    }

}