package com.example.practice3.screens

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (!isGranted) {
                val dialog = AlertDialog.Builder(context)
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
        Button(onClick = {controller.popBackStack()}) {
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
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }
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
