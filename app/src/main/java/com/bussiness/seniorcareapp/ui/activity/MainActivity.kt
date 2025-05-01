package com.bussiness.seniorcareapp.ui.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bussiness.seniorcareapp.R
import com.bussiness.seniorcareapp.databinding.ActivityMainBinding
import com.bussiness.seniorcareapp.databinding.DialogDeleteAccountBinding
import com.bussiness.seniorcareapp.utils.SessionManager
import com.google.android.material.imageview.ShapeableImageView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(binding.drawerLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up navigation controller
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.bottom_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        setupNavigationView()

        // Set initial fragment
        if (sessionManager.isLoggedIn()) {
            navController.navigate(R.id.homeFragment)
        } else if (sessionManager.isSkippedLogin()) {
            navController.navigate(R.id.homeFragment)
        } else {
            navController.navigate(R.id.loginFragment)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val visibleDestinations = setOf(
                R.id.homeFragment,
                R.id.compareFacilitiesFragment,
                R.id.profileFragment,
                R.id.savedFacilitiesFragment
            )

            binding.bottomNavigationView.visibility =
                if (destination.id in visibleDestinations) View.VISIBLE else View.GONE
        }

    }

    private fun setupNavigationView() {
        val navigationView = binding.navigationView
        val headerView = navigationView.getHeaderView(0)
        val nameTextView = headerView.findViewById<TextView>(R.id.name)
        val image = headerView.findViewById<ShapeableImageView>(R.id.profileImage)
        val loginImage = headerView.findViewById<ShapeableImageView>(R.id.loginImage)
        val loginBtn = headerView.findViewById<AppCompatButton>(R.id.loginBtn)
        val btnSignUp = headerView.findViewById<AppCompatButton>(R.id.btnSignUp)
        val drawerCrossIcon = headerView.findViewById<ImageView>(R.id.imageView)

        // Show/hide header info and menu items based on login state
        if (sessionManager.isLoggedIn()) {
            // Simulated user data
            nameTextView.visibility = View.VISIBLE
            image.visibility = View.VISIBLE
            loginImage.visibility = View.GONE
            loginBtn.visibility = View.GONE
            btnSignUp.visibility = View.GONE
            drawerCrossIcon.setOnClickListener { closeDrawer() }

            navigationView.menu.findItem(R.id.navFAQ).isVisible = true
            navigationView.menu.findItem(R.id.navLogout).isVisible = true
        } else if (sessionManager.isSkippedLogin()) {
            nameTextView.visibility = View.GONE
            image.visibility = View.GONE
            loginImage.visibility = View.VISIBLE
            loginBtn.visibility = View.VISIBLE
            btnSignUp.visibility = View.VISIBLE
            drawerCrossIcon.setOnClickListener { closeDrawer() }
            loginBtn.setOnClickListener { val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            }
            btnSignUp.setOnClickListener {
                val intent = Intent(this, AuthActivity::class.java)
                intent.putExtra("targetFragment", "signup")
                startActivity(intent)
            }

            navigationView.menu.findItem(R.id.navFAQ).isVisible = false
            navigationView.menu.findItem(R.id.navLogout).isVisible = false
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navAboutUs -> navController.navigate(R.id.aboutUsFragment)
                R.id.navService -> navController.navigate(R.id.servicesFragment)
                R.id.navSubscriptionPlans -> navController.navigate(R.id.subscriptionFragment)
                R.id.navContactUs -> navController.navigate(R.id.contactUsFragment)
                R.id.navCompareFacility -> navController.navigate(R.id.compareFacilitiesFragment)
                R.id.navAccountPrivacy -> navController.navigate(R.id.aboutUsFragment)
                R.id.navFAQ -> navController.navigate(R.id.faqFragment)
                R.id.navLogout -> {
                    dialogLogout()
                    sessionManager.clearSession()
                }
            }
            closeDrawer()
            true
        }
    }

    fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    @SuppressLint("SetTextI18n")
    private fun dialogLogout() {
        val dialog = Dialog(this)
        val binding = DialogDeleteAccountBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        binding.tickImg.setImageResource(R.drawable.logout)
        binding.title.text = "Logout"
        binding.descriptionText.text = "Are you sure you want to logout?"
        binding.deleteBtn.text = "Yes"
        binding.cancelBtn.text = "No"

        binding.apply {
            deleteBtn.setOnClickListener {
                dialog.dismiss()
                sessionManager.clearSession()
                val intent = Intent(this@MainActivity, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
            cancelBtn.setOnClickListener { dialog.dismiss() }
            crossIcon.setOnClickListener { dialog.dismiss() }
        }

        dialog.apply {
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)

            val displayMetrics = context.resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels
            val marginPx =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, displayMetrics).toInt()
            window?.setLayout(screenWidth - (2 * marginPx), ViewGroup.LayoutParams.WRAP_CONTENT)
            show()
        }
    }
}
