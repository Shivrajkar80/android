package com.example.e_commerce.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileInfoScreen(navController: NavHostController, userSessionViewModel: UserSessionViewModel = viewModel()) {
    val user = userSessionViewModel.user.value
    val updatedName = remember { mutableStateOf(user?.name ?: "") }
    val updatedEmail = remember { mutableStateOf(user?.email ?: "") }
    val updatedPhoneNumber = remember { mutableStateOf(user?.phoneNumber ?: "") }

    // State to track if profile is being edited
    val isEditing = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile Information") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TextField(
                value = updatedName.value,
                onValueChange = { updatedName.value = it },
                label = { Text("Name") },
                enabled = isEditing.value,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = updatedEmail.value,
                onValueChange = { updatedEmail.value = it },
                label = { Text("Email") },
                enabled = isEditing.value,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = updatedPhoneNumber.value,
                onValueChange = { updatedPhoneNumber.value = it },
                label = { Text("Phone Number") },
                enabled = isEditing.value,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isEditing.value) {
                // Save button to save changes
                val coroutineScope = rememberCoroutineScope()
                Button(
                    onClick = {
                        coroutineScope.launch {
                            userSessionViewModel.updateUserDetails(
                                updatedName.value,
                                updatedEmail.value,
                                updatedPhoneNumber.value
                            )
                            isEditing.value = false // Exit editing mode after saving
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save")
                }
            } else {
                // Edit button to enter editing mode
                Button(
                    onClick = { isEditing.value = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Edit")
                }
            }
        }
    }
}
