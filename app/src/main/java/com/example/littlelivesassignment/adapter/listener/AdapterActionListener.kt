package com.example.littlelivesassignment.adapter.listener

import com.example.littlelivesassignment.data.model.*

interface AdapterActionListener {

    interface OnRequestEvent {
        fun onRequestEventDetail(event: ChildEvent?) {}
        fun onRequestCreateEvent(event: ChildEvent?) {}
    }

    interface OnRequestAttendanceRecord {
        fun onRequestAtdRecordDetail(attendanceRecord: AttendanceRecord?) {}
    }

    interface OnRequestMedia {
        fun onRequestMediaDetail(media: PortfolioEvent?) {}
    }

    interface OnRequestStoryPublished {
        fun onRequestStoryPublishedDetail(storyPublished: StoryPublishedEvent?) {}
    }

    interface OnRequestStoryExported {
        fun onRequestStoryExportedDetail(storyExported: StoryExportedEvent?) {}
        fun onRequestDownloadStory(storyExported: StoryExportedEvent?) {}
    }
}