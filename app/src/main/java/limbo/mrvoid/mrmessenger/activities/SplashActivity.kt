package limbo.mrvoid.mrmessenger.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import limbo.mrvoid.mrmessenger.R
import limbo.mrvoid.mrmessenger.firebase.FirestoreClass

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val currentUserId = FirestoreClass().getCurrentUserId()
            if(currentUserId.isNotEmpty()) {
                startActivity(Intent(this@SplashActivity,MainActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
            }
            finish()
        },1500)
    }
}