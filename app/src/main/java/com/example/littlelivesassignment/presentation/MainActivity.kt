package com.example.littlelivesassignment.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.littlelivesassignment.adapter.EventAdapter
import com.example.littlelivesassignment.databinding.ActivityMainBinding
import com.example.littlelivesassignment.presentation.list.EventListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val eventListVM: EventListViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: EventAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initViews()
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            eventListVM.events.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initViews() {
        adapter = EventAdapter()
        binding.rcvEvents.apply {
            adapter = this@MainActivity.adapter
        }
    }
}