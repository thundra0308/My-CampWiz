package limbo.mrvoid.mrmessenger.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import limbo.mrvoid.mrmessenger.R
import limbo.mrvoid.mrmessenger.fragments_main.CameraFragment
import limbo.mrvoid.mrmessenger.fragments_main.ChatListFragment
import limbo.mrvoid.mrmessenger.fragments_main.MyProfileFragment
import limbo.mrvoid.mrmessenger.fragments_main.SettingsFragment

open class ViewPagerMainAdapter(fragmentManager: FragmentManager, lifeCycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifeCycle) {



    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> {
                return CameraFragment()
            }
            1 -> {
                return MyProfileFragment()
            }
            2 -> {
                return ChatListFragment()
            }
            else -> {
                return SettingsFragment()
            }
        }
    }

}