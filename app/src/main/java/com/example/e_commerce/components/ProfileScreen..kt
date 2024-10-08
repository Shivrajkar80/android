package com.example.e_commerce.components

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileScreen(navController: NavHostController, userSessionViewModel: UserSessionViewModel = viewModel()) {
    var selectedTab by remember { mutableStateOf(3) }
    val user by userSessionViewModel.user.observeAsState()
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var showImagePickerDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { AppTopAppBar(title = "EZYDEALS", onAccountClick = { /* No additional action needed */ }) },
        bottomBar = {
            AppBottomNavigationBar(
                navController = navController,
                selectedTab = selectedTab,
                onTabSelected = { tabIndex ->
                    selectedTab = tabIndex
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("My profile", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                val imageUri = profileImageUri ?: user?.profilePictureUrl
                val painter = rememberImagePainter(
                    request = ImageRequest.Builder(LocalContext.current)
                        .data(imageUri)
                        .crossfade(true)
                        .build()
                )

                Image(
                    painter = painter,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .clickable {
                            showImagePickerDialog = true
                        },
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(user?.name ?: "Name", style = MaterialTheme.typography.titleLarge)
                    Text(user?.email ?: "Email", style = MaterialTheme.typography.bodyMedium)
                }
            }

           // ProfileSectionItem("My Orders", "Check orders") { /* Handle click */ }
            ProfileSectionItem("Profile Info", "View information") {
                navController.navigate("profileInfo")
            }
           // ProfileSectionItem("Settings", "Logout") { /* Handle click */ }

            Spacer(modifier = Modifier.height(16.dp))

            AccountSettings(navController, userSessionViewModel)

            if (showImagePickerDialog) {
                HandleImageSelection(
                    cameraPermissionState = cameraPermissionState,
                    onImageSelected = { uri ->
                        profileImageUri = uri
                        userSessionViewModel.updateUserProfilePicture(uri.toString())
                        showImagePickerDialog = false
                    },
                    onDismissRequest = { showImagePickerDialog = false }
                )
            }
        }
    }
}

@Composable
fun ProfileSectionItem(title: String, subtitle: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    ) {
        Text(title, style = MaterialTheme.typography.bodyLarge)
        Text(subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HandleImageSelection(
    cameraPermissionState: com.google.accompanist.permissions.PermissionState,
    onImageSelected: (Uri?) -> Unit,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    val storage = FirebaseStorage.getInstance()
    val coroutineScope = rememberCoroutineScope()

    val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            coroutineScope.launch {
                val downloadUrl = uploadImageToFirebaseStorage(storage, it)
                onImageSelected(downloadUrl)
            }
        }
    }

    val takePictureLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        bitmap?.let {
            val uri = saveBitmapToUri(context, it)
            coroutineScope.launch {
                val downloadUrl = uploadImageToFirebaseStorage(storage, uri)
                onImageSelected(downloadUrl)
            }
        }
    }

    if (cameraPermissionState.status.isGranted) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text("Select Image") },
            text = { Text("Choose an option to select your profile picture.") },
            confirmButton = {
                TextButton(onClick = { pickImageLauncher.launch("image/*") }) {
                    Text("Gallery")
                }
            },
            dismissButton = {
                TextButton(onClick = { takePictureLauncher.launch(null) }) {
                    Text("Camera")
                }
            }
        )
    } else {
        LaunchedEffect(cameraPermissionState) {
            cameraPermissionState.launchPermissionRequest()
        }
    }
}

private suspend fun uploadImageToFirebaseStorage(
    storage: FirebaseStorage,
    uri: Uri
): Uri? {
    return try {
        val fileName = uri.lastPathSegment ?: "default_name"
        val storageRef = storage.reference.child("profile_pictures/$fileName")

        val uploadTask = storageRef.putFile(uri).await()
        val downloadUrl = storageRef.downloadUrl.await()

        downloadUrl
    } catch (e: Exception) {
        null
    }
}

private fun saveBitmapToUri(context: Context, bitmap: Bitmap): Uri {
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
    return Uri.parse(path)
}

@Composable
fun AccountSettings(navController: NavHostController, userSessionViewModel: UserSessionViewModel) {
    Column {
        AccountSettingsItem("Logout", onClick = {
            userSessionViewModel.logout {
                navController.navigate("login") {
                    popUpTo("profile") { inclusive = true }
                }
            }
        })
    }
}

@Composable
fun AccountSettingsItem(option: String, onClick: () -> Unit) {
    Text(
        text = option,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
    )
}
