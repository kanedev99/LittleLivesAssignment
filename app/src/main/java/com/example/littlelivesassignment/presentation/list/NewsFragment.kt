package com.example.littlelivesassignment.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.example.littlelivesassignment.R
import com.example.littlelivesassignment.adapter.EventListAdapter
import com.example.littlelivesassignment.adapter.decoration.DividerItemDecoration
import com.example.littlelivesassignment.data.model.*
import com.example.littlelivesassignment.databinding.FragmentNewsBinding
import com.example.littlelivesassignment.presentation.detail.UserEventFragment
import com.example.littlelivesassignment.utils.ext.gone
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsFragment : Fragment() {

    private val eventListVM: EventListViewModel2 by viewModels()

    private lateinit var binding: FragmentNewsBinding
    private lateinit var adapter: EventListAdapter

    companion object {

        fun newInstance(extras: Bundle?): NewsFragment {
            return NewsFragment().apply { arguments = extras }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    private fun initViews() {
        adapter = EventListAdapter().apply {
            callback = object: EventListAdapter.Callback {
                override fun onRequestAttendanceRecord(attendanceRecord: AttendanceRecord?) {
                    super.onRequestAttendanceRecord(attendanceRecord)
                    Toast.makeText(this@NewsFragment.context, "AttendanceRecord", Toast.LENGTH_SHORT).show()
                    navigateToEventDetail(UserEventFragment.EVENT_TYPE_KEY, getString(R.string.check_in_out_title))
                }

                override fun onRequestCreateEvent(event: ChildEvent?) {
                    super.onRequestCreateEvent(event)
                    Toast.makeText(this@NewsFragment.context, "ChildEvent", Toast.LENGTH_SHORT).show()
                    navigateToEventDetail(UserEventFragment.EVENT_TYPE_KEY, getString(R.string.create_event_title))
                }

                override fun onRequestMedia(media: PortfolioEvent?) {
                    super.onRequestMedia(media)
                    Toast.makeText(this@NewsFragment.context, "PortfolioEvent", Toast.LENGTH_SHORT).show()
                    navigateToEventDetail(UserEventFragment.EVENT_TYPE_KEY, getString(R.string.portfolio_event_title))
                }

                override fun onRequestStoryExported(storyExported: StoryExportedEvent?) {
                    super.onRequestStoryExported(storyExported)
                    Toast.makeText(this@NewsFragment.context, "StoryExportedEvent", Toast.LENGTH_SHORT).show()
                    navigateToEventDetail(UserEventFragment.EVENT_TYPE_KEY, getString(R.string.portfolio_event_title))
                }

                override fun onRequestStoryPublished(storyPublished: StoryPublishedEvent?) {
                    super.onRequestStoryPublished(storyPublished)
                    Toast.makeText(this@NewsFragment.context, "StoryPublishedEvent", Toast.LENGTH_SHORT).show()
                    navigateToEventDetail(UserEventFragment.EVENT_TYPE_KEY, getString(R.string.portfolio_event_title))
                }
            }
        }
        binding.rcvEvents.apply {
            adapter = this@NewsFragment.adapter
            AppCompatResources.getDrawable(this@NewsFragment.requireContext(), R.drawable.drawable_line_divider)
                ?.let { DividerItemDecoration(this@NewsFragment.requireContext(), it) }
                ?.let { addItemDecoration(it) }
        }
    }

    private fun navigateToEventDetail(key: String, eventType: String) {
        val transaction: FragmentTransaction =
            this.activity?.supportFragmentManager?.beginTransaction() ?: return

        transaction.replace(R.id.fragment_container, UserEventFragment.newInstance(Bundle().apply {
            putString(key, eventType)
        }) )
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun initObservers() {
        eventListVM.events.observe(viewLifecycleOwner) {
            binding.progressBar.gone()
            adapter.buildDataMap(it)
            adapter.notifyDataSetChanged()
        }
    }

}