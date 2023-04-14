package com.example.littlelivesassignment.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.littlelivesassignment.R
import com.example.littlelivesassignment.adapter.EventAdapter
import com.example.littlelivesassignment.adapter.EventListAdapter
import com.example.littlelivesassignment.adapter.decoration.DividerItemDecoration
import com.example.littlelivesassignment.databinding.ActivityMainBinding
import com.example.littlelivesassignment.presentation.list.EventListViewModel
import com.example.littlelivesassignment.presentation.list.EventListViewModel2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val eventListVM: EventListViewModel by viewModels()
    private val eventListVM2: EventListViewModel2 by viewModels()

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: EventAdapter
    private lateinit var adapter2: EventListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initViews()
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            eventListVM.events.collect {
                adapter.submitData(it)
            }
        }

        eventListVM2.events.observe(this, Observer {
            adapter2.submitList(it)
        })
    }

    private fun initViews() {
        adapter = EventAdapter()
        adapter2 = EventListAdapter()
        binding.rcvEvents.apply {
            adapter = this@MainActivity.adapter2
            AppCompatResources.getDrawable(this@MainActivity, R.drawable.drawable_line_divider)
                ?.let { DividerItemDecoration(this@MainActivity, it) }
                ?.let { addItemDecoration(it) }
        }
    }
}