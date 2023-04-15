package com.example.littlelivesassignment.data.model

sealed class EventSnapshot

data class ChildEvent(
    val childId: String,
    val childName: String,
    val eventDate: String,
    val eventTitle: String
) : EventSnapshot()

data class ActivityEvent(
    val activityId: String,
    val activityClass: String?,
    val activitySubType: String?,
    val activityType: String?,
    val endTime: String?,
    val referenceObject: ReferenceObject?,
    val remarks: String?,
    val schoolId: Int?,
    val startTime: String?
) : EventSnapshot()

data class AttendanceRecord(
    val checkInthumb: String?,
    val checkinUrl: String?,
    val msgParams: MsgParams,
    val referenceObj: ReferenceObject?,
    val schoolId: Int?
) : EventSnapshot()

data class StoryPublishedEvent(
    val storyId: String,
    val childId: String?,
    val childName: String?,
    val storyTitle: String?,
    val schoolId: Int?,
    val schoolName: String?
) : EventSnapshot()

data class PortfolioEvent(
    val albumId: String,
    val albumName: String?,
    val caption: String?,
    val childId: String?,
    val childName: String?,
    val fileId: String?,
    val imageUrl: String?,
    val referenceObj: ReferenceObject,
    val schoolId: Int?,
    val schoolName: String?,
    val teacherName: String?
) : EventSnapshot()

data class StoryExportedEvent(
    val exp: String?,
    val school_id: Int?,
    val url: String
) : EventSnapshot()

data class MsgParams(
    val checkInDate: String,
    val childName: String,
    val rawCheckInDate: String?,
    val schoolName: String?
)

data class ReferenceObject(
    val type: String?,
    val value: String?
)