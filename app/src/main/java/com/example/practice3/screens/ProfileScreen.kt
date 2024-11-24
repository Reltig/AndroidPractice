package com.example.practice3.screens

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.practice3.utils.SystemBroadcastReceiver
import com.example.practice3.viewModels.BookViewModel
import com.example.practice3.viewModels.ProfileViewModel
import org.koin.androidx.compose.koinViewModel
import java.io.File
//
@Composable
fun ProfileScreen(controller: NavHostController) {
    val viewModel = koinViewModel<ProfileViewModel>()
    val state = viewModel.viewState
    val context = LocalContext.current

    InitBroadcastReceiver(context)

    Column(Modifier.fillMaxSize()) {
        AsyncImage(
            model = state.uri,
            contentDescription = "avatar",
            modifier = Modifier.size(128.dp)
        )
        Text("Name: ${state.name}")
        Button(onClick = {enqueueDownloadRequest(state.url, context)}) {
            Text("Download PDF")
        }
        Button(onClick = { controller.navigate("edit_profile") }) {
            Text("Редактировать")
        }
    }
}

@Composable
private fun InitBroadcastReceiver(context: Context) {
    SystemBroadcastReceiver(
        systemAction = "android.intent.action.DOWNLOAD_COMPLETE",
        onSystemEvent = { intent ->
            if (intent?.action == "android.intent.action.DOWNLOAD_COMPLETE") {
                val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
                if (id != -1L) {
                    navigateToDownloadedInvoice(context)
                }
            }
        })
}

private fun enqueueDownloadRequest(
    url: String,
    context: Context
) {
    val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(url))
    with(request) {
        setTitle("Test pdf")
        setMimeType("pdf")
        setDescription("Downloading pdf...")
        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            "test.pdf"
        )
    }
    val manager: DownloadManager =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    manager.enqueue(request)
}

private fun navigateToDownloadedInvoice(context: Context) {
    try {
        val file = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
            ),
            "Test.pdf"
        )
        val uri = FileProvider.getUriForFile(
            context,
            context.applicationContext?.packageName + ".provider",
            file
        )
        val intent = Intent(Intent.ACTION_VIEW)
        with(intent) {
            setDataAndType(
                uri,
                "application/pdf"
            )
            flags =
                Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}