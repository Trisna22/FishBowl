package com.example.fishbowl

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.fishbowl.databinding.ActivityMainBinding
import com.example.fishbowl.modals.Player
import com.example.fishbowl.modals.Question

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController;

    companion object {
        var lastPlayers: MutableList<String> = mutableListOf()
        var lastQuestions: MutableList<Question> = mutableListOf()
        var questionMap: MutableMap<Question, String> = mutableMapOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
                as NavHostFragment
        navController = navHostFragment.navController

    }
}