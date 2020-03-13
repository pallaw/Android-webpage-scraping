package com.pallaw.pallaw_pathak.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.pallaw.pallaw_pathak.R
import com.pallaw.pallaw_pathak.databinding.ActivityMainBinding
import com.pallaw.pallaw_pathak.viewmodel.MainViewModel

/**
 * Created by Pallaw Pathak on 2020-03-13. - https://www.linkedin.com/in/pallaw-pathak-a6a324a1/
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()

    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        binding.apply {
            lifecycleOwner = this@MainActivity
            mainViewModel = viewModel
        }
        setSupportActionBar(binding.toolbar)
    }

}
