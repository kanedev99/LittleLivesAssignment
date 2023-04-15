package com.example.littlelivesassignment.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.littlelivesassignment.R
import com.example.littlelivesassignment.databinding.FragmentUserEventBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserEventFragment : Fragment() {

    private lateinit var binding: FragmentUserEventBinding

    private var title = ""

    companion object {

        const val EVENT_TYPE_KEY = "event_type"

        fun newInstance(extras: Bundle?): UserEventFragment {
            return UserEventFragment().apply { arguments = extras }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserEventBinding.inflate(inflater, container, false)

        title = arguments?.getString(EVENT_TYPE_KEY).toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = title

        binding.btnBack.setOnClickListener {
            if ((activity?.supportFragmentManager?.backStackEntryCount ?: 0) > 0) {
                activity?.supportFragmentManager?.popBackStack()
            } else {
                activity?.finish()
            }
        }
    }



}