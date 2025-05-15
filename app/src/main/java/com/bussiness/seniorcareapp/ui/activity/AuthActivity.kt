package com.bussiness.seniorcareapp.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.databinding.ActivityAuthBinding
import com.bussiness.seniorcareapp.databinding.ActivityMainBinding // Import the generated binding class

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the binding
        binding = ActivityAuthBinding.inflate(layoutInflater)

        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val targetFragment = intent.getStringExtra("targetFragment")
        val backNav = intent.getStringExtra("backNav")

        if (backNav == "Terms & condition") {
            navController.navigate(R.id.registerFragment)
        } else if (backNav == "Privacy Policy") {
            navController.navigate(R.id.registerFragment)
        }


        if (targetFragment == "signup") {
            navController.navigate(R.id.registerFragment)
        }

    }
}
