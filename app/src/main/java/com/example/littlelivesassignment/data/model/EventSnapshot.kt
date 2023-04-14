package com.example.littlelivesassignment.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class EventSnapshot

//@JsonAdapter(ChildEventTypeAdapter::class)
@Entity(tableName = "child_events")
data class ChildEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val childId: String,
    val childName: String,
    val eventDate: String,
    val eventTitle: String
) : EventSnapshot()

//@JsonAdapter(ActivityEventTypeAdapter::class)
@Entity(tableName = "activity_events")
data class ActivityEvent(
    @PrimaryKey
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

@Entity(tableName = "attendance_tracking_events")
data class AttendanceTrackingEvent(
    @PrimaryKey(autoGenerate = true)
    val attendanceTrackingId: Int = 0,
    val checkInthumb: String?,
    val checkinUrl: String?,
    @Embedded
    val msgParams: MsgParams,
    @Embedded
    val referenceObj: ReferenceObject?,
    val schoolId: Int?
) : EventSnapshot()

@Entity(tableName = "story_published_events")
data class StoryPublishedEvent(
    @PrimaryKey
    val storyId: String,
    val childId: String?,
    val childName: String?,
    val storyTitle: String?,
    val schoolId: Int?,
    val schoolName: String?
) : EventSnapshot()

@Entity(tableName = "portfolio_events")
data class PortfolioEvent(
    @PrimaryKey
    val albumId: String,
    val albumName: String?,
    val caption: String?,
    val childId: String?,
    val childName: String?,
    val fileId: String?,
    val imageUrl: String?,
    @Embedded
    val referenceObj: ReferenceObject,
    val schoolId: Int?,
    val schoolName: String?,
    val teacherName: String?
) : EventSnapshot()

@Entity(tableName = "story_exported_events")
data class StoryExportedEvent(
    @PrimaryKey(autoGenerate = true)
    val storyExportedId: Int = 0,
    val exp: String?,
    val school_id: Int?,
    val url: String
) : EventSnapshot()

@Entity(tableName = "msg_params")
data class MsgParams(
    @PrimaryKey
    val attendanceRecordId: String,
    val checkInDate: String,
    val childName: String,
    val rawCheckInDate: String?,
    val schoolName: String?
)

@Entity(tableName = "reference_object")
data class ReferenceObject(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String?,
    val value: String?
)