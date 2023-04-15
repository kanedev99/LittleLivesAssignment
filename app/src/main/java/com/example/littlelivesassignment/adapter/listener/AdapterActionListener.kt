package com.example.littlelivesassignment.adapter.listener

import com.example.littlelivesassignment.data.model.*

interface AdapterActionListener {

    interface OnRequestEvent {
        fun onRequestCreateEvent(event: ChildEvent?) {}
    }

    interface OnRequestAttendanceRecord {
        fun onRequestAttendanceRecord(attendanceRecord: AttendanceRecord?) {}
    }

    interface OnRequestMedia {
        fun onRequestMedia(media: PortfolioEvent?) {}
    }

    interface OnRequestStoryPublished {
        fun onRequestStoryPublished(storyPublished: StoryPublishedEvent?) {}
    }

    interface OnRequestStoryExported {
        fun onRequestStoryExported(storyExported: StoryExportedEvent?) {}
    }
}