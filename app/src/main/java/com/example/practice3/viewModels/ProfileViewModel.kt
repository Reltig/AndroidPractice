package com.example.practice3.viewModels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice3.data.DataPreferences
import com.example.practice3.state.ProfileState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(
    val context: Context
): ViewModel() {
    private val mutableState = MutableProfileState()
    val viewState = mutableState as ProfileState

    init {
        initState()
    }

    private fun initState() {
        viewModelScope.launch {
            mutableState.name = DataPreferences.getProfileName(context).first()
            mutableState.uri = DataPreferences.getProfileUri(context).first()
            mutableState.url = DataPreferences.getProfileUrl(context).first()
        }
    }

    fun setProfileName(name: String) {
        mutableState.name = name
        viewModelScope.launch {
            DataPreferences.saveProfileName(context, name)
        }
    }

    fun setProfileUrl(url: String) {
        mutableState.url = url
        viewModelScope.launch {
            DataPreferences.saveProfileUrl(context, url)
        }
    }

    fun setProfileUri(uri: Uri) {
        uri.let{mutableState.uri = uri}
        viewModelScope.launch {
            DataPreferences.saveProfileUri(context, uri)
        }
    }

    fun onAvatarClick() {
        mutableState.showSelectImage = true;
    }

    fun onAvatarSelectDismiss() {
        mutableState.showSelectImage = false;
    }

    fun onPermissionOpened() {
        mutableState.showPermission = true
    }

    fun onPermissionClosed() {
        mutableState.showPermission = false
    }

    private class MutableProfileState : ProfileState {
        override var name: String by mutableStateOf("Shmakov Danil")
        override var uri: Uri by mutableStateOf(Uri.parse("https://placehold.co/400"))
        override var url: String by mutableStateOf("https://elibrary.ru/download/elibrary_44394445_69180276.pdf")
        override var error: String? by mutableStateOf(null)
        override var loading: Boolean by mutableStateOf(false)
        override var showSelectImage: Boolean by mutableStateOf(false)
        override var showPermission: Boolean by mutableStateOf(false)
    }
}