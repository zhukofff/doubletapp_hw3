package com.example.doubletapp_hw2

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.doubletapp_hw2.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

/*
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
*/


    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navController : NavController
    private lateinit var drawerToggle : ActionBarDrawerToggle
    private lateinit var habitsFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.title = "Habit"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.navDrawerLayout,
            binding.toolbar.toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )

        binding.navDrawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        setupDrawerContent(binding.navigationDrawer)
        drawerToggle.isDrawerIndicatorEnabled = true

        if (savedInstanceState != null) {
            Log.v("habit", "fragments = ${supportFragmentManager.fragments}")
            /*habitsFragment =
                supportFragmentManager.getFragment(savedInstanceState, FragmentTags.MainHabits.name)!!*/
        }

        if (savedInstanceState == null) {
            habitsFragment = MainHabitsFragment()
            val fragmentManager =
                supportFragmentManager.beginTransaction().add(R.id.fragmentHolder, habitsFragment, FragmentTags.MainHabits.name)
            fragmentManager.commit()
        }
        /*val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
        navController = navHostFragment!!.findNavController()*/
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.v("habit", "On save instance state in activity ${supportFragmentManager.fragments}")
        for (fr in supportFragmentManager.fragments) {

            supportFragmentManager.putFragment(outState, fr.tag.toString(), fr)
        }

        //getSupportFragmentManager().putFragment(outState, "myFragmentName", habitsFragment);

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.side_navigation, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupDrawerContent(navView: NavigationView) {

        navView.setNavigationItemSelectedListener {
            selectDrawerItem(it)
            true
        }

    }

    private fun selectDrawerItem(item: MenuItem) {
        var fragment : Fragment? = null
        when(item.itemId) {
            R.id.home -> {
                fragment = MainHabitsFragment()

            }
            R.id.info_about_app -> {
                fragment = InformationAboutAppFragment()
            }
        }
        if (fragment != null) {
            val fragmentManager =
                supportFragmentManager.beginTransaction().replace(R.id.fragmentHolder, fragment)
            fragmentManager.commit()
        }
        item.setChecked(true)
        title = item.title
        binding.navDrawerLayout.closeDrawers()
    }


}