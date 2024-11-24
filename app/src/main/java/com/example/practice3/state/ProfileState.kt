package com.example.practice3.state

import android.net.Uri
import com.example.practice3.models.Book

interface ProfileState {
    val name: String
    val uri: Uri
    val url: String
    val error: String?
    var loading: Boolean
    var showSelectImage: Boolean
    var showPermission: Boolean
}