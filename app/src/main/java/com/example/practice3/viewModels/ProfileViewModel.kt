package com.example.practice3.viewModels

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice3.data.DataPreferences
import com.example.practice3.receiver.NotificationsReceiver
import com.example.practice3.state.ProfileState
import com.example.practice3.utils.tryParse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class ProfileViewModel(
    val context: Context
): ViewModel() {
    private val mutableState = MutableProfileState()
    val viewState = mutableState as ProfileState
    private val formatter = DateTimeFormatter.ofPattern("HH:mm")

    init {
        initState()
    }

    private fun initState() {
        viewModelScope.launch {
            mutableState.name = DataPreferences.getProfileName(context).first()
            mutableState.uri = DataPreferences.getProfileUri(context).first()
            mutableState.url = DataPreferences.getProfileUrl(context).first()
            DataPreferences.getTime(context).first().let {
                tryParse(it)?.let { time ->
                    mutableState.time = time
                    updateTimeString()
                }
            }
        }
    }

    fun onDoneClicked() {
        validateTime()
        if (mutableState.timeError != null) return
        viewModelScope.launch {
            DataPreferences.saveProfileName(context, mutableState.name)
            DataPreferences.saveProfileUri(context, mutableState.uri)
            DataPreferences.saveProfileUrl(context, mutableState.url)
            DataPreferences.saveTime(context, mutableState.time)
            Log.d("AAA", mutableState.time.toString())
            saveNotification()
            //back()
        }
    }

    fun onTimeInputClicked() {
        mutableState.showTimePicker = true
    }
    fun onTimeChanged(time: String) {
        mutableState.timeString = time
        validateTime()
    }
    fun onTimeConfirmed(h: Int, m: Int) {
        mutableState.time = LocalTime.of(h, m)
        mutableState.timeError = null
        updateTimeString()
        onTimeDialogDismiss()
    }
    fun onTimeDialogDismiss() {
        mutableState.showTimePicker = false
    }
    private fun saveNotification() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val dateTime = LocalDateTime.of(LocalDate.now(), viewState.time)
        val zoneId = ZoneId.systemDefault()
        Log.d("ZONE", zoneId.toString());
        val timeInMillis = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val notifyIntent = Intent(context, NotificationsReceiver::class.java)
        notifyIntent.putExtras(
            Bundle().apply {
                putString("NOTIFICATION", "Пора на пару, ${viewState.name}!")
            }
        )
        val notifyPendingIntent = PendingIntent.getBroadcast(
            context,
            0, //notificationModel.id,
            notifyIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                notifyPendingIntent
            )
        } catch (e: SecurityException) {
            Log.d("alarmManager", "Failed to set reminder")
        }
    }
    private fun validateTime() {
        try {
            mutableState.time = LocalTime.parse(mutableState.timeString, formatter)
            mutableState.timeError = null
        } catch (e: Exception) {
            mutableState.timeError = "Некорректный формат времени"
        }
    }
    private fun updateTimeString() {
        mutableState.timeString = formatter.format(viewState.time)
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
        override var time: LocalTime by mutableStateOf(LocalTime.now())
        override var timeString: String by mutableStateOf("")
        override var timeError: String? by mutableStateOf(null)
        override var showSelectImage: Boolean by mutableStateOf(false)
        override var showPermission: Boolean by mutableStateOf(true)
        override var showTimePicker: Boolean by mutableStateOf(false)
    }
}