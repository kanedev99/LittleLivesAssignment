package com.example.littlelivesassignment.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.littlelivesassignment.R
import com.example.littlelivesassignment.adapter.recycler.EventAdapter
import com.example.littlelivesassignment.adapter.decoration.DividerItemDecoration
import com.example.littlelivesassignment.adapter.recycler.LoadingStateAdapter
import com.example.littlelivesassignment.data.model.AttendanceRecord
import com.example.littlelivesassignment.data.model.ChildEvent
import com.example.littlelivesassignment.data.model.StoryExportedEvent
import com.example.littlelivesassignment.data.model.StoryPublishedEvent
import com.example.littlelivesassignment.databinding.FragmentNewsBinding
import com.example.littlelivesassignment.presentation.detail.UserEventFragment
import com.example.littlelivesassignment.utils.ext.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NewsFragment : Fragment() {

    private val userEventViewModel: UserEventViewModel by viewModels()

    private lateinit var binding: FragmentNewsBinding
    private lateinit var adapter: EventAdapter

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

        initView()
        collectUiState()
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

    private fun initView() {
        adapter = EventAdapter().apply {
            callback = object: EventAdapter.Callback {
                override fun onRequestAtdRecordDetail(attendanceRecord: AttendanceRecord?) {
                    super.onRequestAtdRecordDetail(attendanceRecord)
                    Toast.makeText(this@NewsFragment.context, "AttendanceRecord", Toast.LENGTH_SHORT).show()
                    navigateToEventDetail(UserEventFragment.EVENT_TYPE_KEY, getString(R.string.check_in_out_title))
                }

                override fun onRequestEventDetail(event: ChildEvent?) {
                    super.onRequestEventDetail(event)
                    Toast.makeText(this@NewsFragment.context, "ChildEvent", Toast.LENGTH_SHORT).show()
                    navigateToEventDetail(UserEventFragment.EVENT_TYPE_KEY, getString(R.string.create_event_title))
                }

                override fun onRequestStoryExportedDetail(storyExported: StoryExportedEvent?) {
                    super.onRequestStoryExportedDetail(storyExported)
                    Toast.makeText(this@NewsFragment.context, "StoryExportedEvent", Toast.LENGTH_SHORT).show()
                    navigateToEventDetail(UserEventFragment.EVENT_TYPE_KEY, getString(R.string.portfolio_event_title))
                }

                override fun onRequestStoryPublishedDetail(storyPublished: StoryPublishedEvent?) {
                    super.onRequestStoryPublishedDetail(storyPublished)
                    Toast.makeText(this@NewsFragment.context, "StoryPublishedEvent", Toast.LENGTH_SHORT).show()
                    navigateToEventDetail(UserEventFragment.EVENT_TYPE_KEY, getString(R.string.portfolio_event_title))
                }

                override fun onRequestCreateEvent(event: ChildEvent?) {
                    super.onRequestCreateEvent(event)
                    Toast.makeText(this@NewsFragment.context, "Create Event", Toast.LENGTH_SHORT).show()
                }

                override fun onRequestDownloadStory(storyExported: StoryExportedEvent?) {
                    super.onRequestDownloadStory(storyExported)
                    Toast.makeText(this@NewsFragment.context, "Download story", Toast.LENGTH_SHORT).show()
                }
            }

            withLoadStateFooter(
                footer = LoadingStateAdapter { adapter.retry() }
            )

            addLoadStateListener { loadState ->
                binding.rcvEvents.isVisible     = loadState.source.refresh is LoadState.NotLoading
                binding.progressBar.isVisible   = loadState.source.refresh is LoadState.Loading
                binding.btnRetry.isVisible      = loadState.source.refresh is LoadState.Error

                // Handle the error case
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> {
                        binding.btnRetry.visible()
                        loadState.refresh as LoadState.Error
                    }
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(this@NewsFragment.context, it.error.message, Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.rcvEvents.apply {
            adapter = this@NewsFragment.adapter
            AppCompatResources.getDrawable(this@NewsFragment.requireContext(), R.drawable.drawable_line_divider)
                ?.let { DividerItemDecoration(this@NewsFragment.requireContext(), it) }
                ?.let { addItemDecoration(it) }

            setHasFixedSize(true)
        }
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            userEventViewModel.getUserEvents().collectLatest { events ->
                Log.d("Kane", "collectUiState: Entry")
                adapter.submitData(events)
            }
        }
    }

}