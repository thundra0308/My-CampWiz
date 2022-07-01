package limbo.mrvoid.mrmessenger.activities

import android.graphics.Color
import android.icu.util.UniversalTimeScale
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import limbo.mrvoid.mrmessenger.R
import limbo.mrvoid.mrmessenger.adapters.ViewPagerMainAdapter
import limbo.mrvoid.mrmessenger.databinding.ActivityMainBinding
import limbo.mrvoid.mrmessenger.fragments_main.ChatListFragment
import limbo.mrvoid.mrmessenger.fragments_main.MyProfileFragment
import limbo.mrvoid.mrmessenger.fragments_main.SettingsFragment

class MainActivity : BaseActivity() {

    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupChipBottomNavigation()
        setActionBar("Inb-oX")
        val viewPager = findViewById<ViewPager2>(R.id.viewPager_main)
        viewPager.setCurrentItem(2,false)

    }

    private fun setupChipBottomNavigation() {
        val viewPager = findViewById<ViewPager2>(R.id.viewPager_main)
        viewPager.adapter = ViewPagerMainAdapter(supportFragmentManager,lifecycle)
        val chipNavigationBar = findViewById<ChipNavigationBar>(R.id.bootomchipnav)
        chipNavigationBar.setItemSelected(R.id.bootomchipnav,true)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position) {
                    0 -> {
                        chipNavigationBar.setItemSelected(R.id.cameraAtten)
                    }
                    1 -> {
                        chipNavigationBar.setItemSelected(R.id.myprofile)
                    }
                    2 -> {
                        chipNavigationBar.setItemSelected(R.id.chat)
                    }
                    3 -> {
                        chipNavigationBar.setItemSelected(R.id.settings)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

        })
        chipNavigationBar.setOnItemSelectedListener {
            when(it) {
                R.id.myprofile -> {
                    viewPager.currentItem = 1
                    setActionBar("Accoun-t")
                }
                R.id.chat -> {
                    viewPager.currentItem = 2
                    setActionBar("Inb-x")
                }
                R.id.settings -> {
                    viewPager.currentItem = 3
                    setActionBar("Sett-g")
                }
                R.id.cameraAtten -> {
                    viewPager.currentItem = 0
                    setActionBar("ATTENDANCE")
                }
            }
        }
    }

    private fun setActionBar(title: String) {
        setSupportActionBar(binding?.appbar?.toolbarmainacivity)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = title
        binding?.appbar?.toolbarmainacivity?.setTitleTextColor(Color.parseColor("#AC8DDA"))
        binding?.appbar?.toolbarmainacivity?.isTitleCentered = true
        binding?.appbar?.toolbarmainacivity?.setNavigationIcon(R.drawable.ic_hamburger0)
        binding?.appbar?.toolbarmainacivity?.setNavigationOnClickListener {
            if(binding?.drawerlayout?.isDrawerOpen(GravityCompat.START)==true) {
                binding?.drawerlayout?.closeDrawer(GravityCompat.START)
            } else {
                binding?.drawerlayout?.openDrawer(GravityCompat.START)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(binding?.drawerlayout?.isDrawerOpen(GravityCompat.START)==true) {
            binding?.drawerlayout?.closeDrawer(GravityCompat.START)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}