package com.sedat.movieappv2.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sedat.movieappv2.R
import com.sedat.movieappv2.databinding.ActivityMainBinding
import com.sedat.movieappv2.util.hide
import com.sedat.movieappv2.util.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigationController = findNavController(R.id.fragment_container_view)
        binding.bottomNavView.setupWithNavController(navigationController)

        navigationController.addOnDestinationChangedListener{_, destination,_ ->
            if(destination.id == R.id.movieDetailsFragment)
                binding.bottomNavView.hide()
            else
                binding.bottomNavView.show()
        }
    }
}