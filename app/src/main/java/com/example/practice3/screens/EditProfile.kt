package com.example.practice3.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.res.painterResource
import androidx.activity.result.launch
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.Icon
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.practice3.R
import com.example.practice3.viewModels.ProfileViewModel
import org.koin.androidx.compose.koinViewModel
import org.threeten.bp.LocalTime
import java.io.File
import java.util.Date

@Composable
fun EditProfile(controller: NavHostController) {
    val viewModel = koinViewModel<ProfileViewModel>()
    val state = viewModel.viewState
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val pickMedia: ActivityResultLauncher<PickVisualMediaRequest> =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let { viewModel.setProfileUri(it) }
        }
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { map: Map<String, Boolean> ->
            if (map.values.contains(false)) {
                val dialog = android.app.AlertDialog.Builder(context)
                    .setMessage("Error")
                    .setCancelable(false)
                    .setPositiveButton("OK") { _, _ ->
                        controller.popBackStack()
                    }
                dialog.show()
            }
            viewModel.onPermissionClosed()
        }

    val mGetContent = rememberLauncherForActivityResult<Uri, Boolean>(
        ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            viewModel.setProfileUri(imageUri ?: Uri.EMPTY)
        }
    }


    Column(Modifier.fillMaxSize()) {
        AsyncImage(
            model = state.uri,
            contentDescription = "Image uri",
            modifier = Modifier
                .clip(CircleShape)
                .size(128.dp)
                .clickable { viewModel.onAvatarClick() }
        )
        TextField(
            value = state.name,
            onValueChange = {viewModel.setProfileName(it)}
        )
        TextField(
            value = state.url,
            onValueChange = {viewModel.setProfileUrl(it)}
        )
        TextField(
            value = state.timeString,
            onValueChange = { viewModel.onTimeChanged(it) },
            label = { Text("Время любимой пары") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            isError = state.timeError != null,
            trailingIcon = {
                Icon(
                    painterResource(id = R.drawable.timer),
                    null,
                    modifier = Modifier.clickable { viewModel.onTimeInputClicked() }.size(10.dp))
            }
        )
        state.timeError?.let {
            Text(
                it,
                color = MaterialTheme.colorScheme.error,
            )
        }
        if (state.showTimePicker) {
            DialWithDialogExample(
                onConfirm = { h, m -> viewModel.onTimeConfirmed(h, m) },
                onDismiss = { viewModel.onTimeDialogDismiss() },
                time = state.time
            )
        }
        Button(onClick = {viewModel.onDoneClicked(); controller.popBackStack()}) {
            Text("Назад")
        }
    }

    fun onCameraSelected() {
        val baseDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        val pictureFile = File(baseDir, "picture_${Date().time}.jpg")
        imageUri = FileProvider.getUriForFile(
            context,
            context.packageName + ".provider",
            pictureFile
        )
        imageUri?.let { mGetContent.launch(it) }
    }

    if (state.showPermission) {
        LaunchedEffect(Unit) {
            val permissions = mutableListOf<String>()
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }

            if (
                android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissions.add(Manifest.permission.POST_NOTIFICATIONS)
            }
            requestPermissionLauncher.launch(permissions.toTypedArray())

        }
    }

    if (state.showSelectImage) {
        Dialog(onDismissRequest = {viewModel.onAvatarSelectDismiss()}) {
            Surface {
                Column {
                    Text(
                        text = stringResource(R.string.camera),
                        Modifier.clickable {
                            onCameraSelected()
                            viewModel.onAvatarSelectDismiss()
                        }
                    )
                    Text(text = stringResource(R.string.gallery),
                        Modifier.clickable {
                            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            viewModel.onAvatarSelectDismiss()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialWithDialogExample(
    onConfirm: (Int, Int) -> Unit,
    onDismiss: () -> Unit,
    time: LocalTime
) {
    val timePickerState = rememberTimePickerState(
        initialHour = time.hour,
        initialMinute = time.minute,
        is24Hour = true,
    )
    TimePickerDialog(
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(timePickerState.hour, timePickerState.minute) }
    ) {
        TimePicker(
            state = timePickerState,
        )
    }
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Отмена")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        text = { content() }
    )
}
