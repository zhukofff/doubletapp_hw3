package com.example.doubletapp_hw2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.doubletapp_hw2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

/*
    private val binding: ActivityMainBinding by viewBinding()
*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val habitsFragment = HabitsFragment()
        val fragmentManager = supportFragmentManager.beginTransaction().add(R.id.fragmentHolder, habitsFragment)
        fragmentManager.commit()
    }
}