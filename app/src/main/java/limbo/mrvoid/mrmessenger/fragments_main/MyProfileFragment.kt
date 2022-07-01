package limbo.mrvoid.mrmessenger.fragments_main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import limbo.mrvoid.mrmessenger.R
import limbo.mrvoid.mrmessenger.activities.IntroActivity
import limbo.mrvoid.mrmessenger.activities.MainActivity
import limbo.mrvoid.mrmessenger.databinding.FragmentMyProfileBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyProfileFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentMyProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMyProfileBinding.inflate(layoutInflater)
        binding?.btnsignoutmyprofile?.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity,IntroActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            activity?.finish()
        }
        return binding?.root
    }

}