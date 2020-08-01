package com.vjet.slambook.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.vjet.slambook.R
import com.vjet.slambook.databinding.ActivityMainBinding
import com.vjet.slambook.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        toolbar = binding.toolbar
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val navController = findNavController(R.id.fragment)
        toolbar.setupWithNavController(navController, AppBarConfiguration(navController.graph))
        navController.addOnDestinationChangedListener { _, destination, arguments ->
            when (destination.id) {
                R.id.fragment_edit -> {
                    if (arguments?.getString("action") == "Edit") toolbar.inflateMenu(R.menu.menu_edit)
                }
                else -> toolbar.menu.clear()
            }
        }
    }
}