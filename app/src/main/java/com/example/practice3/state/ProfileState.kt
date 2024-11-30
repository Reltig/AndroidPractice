package com.example.practice3.state

import android.net.Uri
import com.example.practice3.models.Book
import org.threeten.bp.LocalTime

interface ProfileState {
    val name: String
    val uri: Uri
    val url: String
    val error: String?
    var loading: Boolean
    val time: LocalTime
    val timeString: String
    val timeError: String?
    var showSelectImage: Boolean
    var showPermission: Boolean
    val showTimePicker: Boolean
}