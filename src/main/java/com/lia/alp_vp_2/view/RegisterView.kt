package com.lia.alp_vp_2.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lia.alp_vp_2.R
import com.lia.alp_vp_2.ui.theme.ALP_VP_2Theme
import com.lia.alp_vp_2.uiState.AuthenticationResult
import com.lia.alp_vp_2.viewmodel.AuthenticationViewModel
import androidx.compose.material.TextField
import androidx.compose.runtime.collectAsState



@Composable
fun RegisterView(
    navController: NavController,
    viewModel: AuthenticationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.authResult.collect { result ->
            when (result) {
                is AuthenticationResult.Success -> {
                    navController.navigate("Home") {
                        popUpTo("SignUp") { inclusive = true }
                    }
                }
                is AuthenticationResult.Error -> {
                    // Show error (you might want to add a Snackbar or similar)
                }
                AuthenticationResult.Loading -> {
                    // Show loading state
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_gedung_uc),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.3f)
        )

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp),
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp)
                    .padding(top = 5.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                RegisterCustomTextField(
                    label = "First Name",
                    placeholder = "First Name",
                    value = uiState.firstName,
                    onValueChange = { value: String -> viewModel.updateField("firstName", value) }
                )
                Spacer(modifier = Modifier.height(8.dp))

                RegisterCustomTextField(
                    label = "Last Name",
                    placeholder = "Last Name",
                    value = uiState.lastName,
                    onValueChange = { value: String -> viewModel.updateField("lastName", value) }
                )
                Spacer(modifier = Modifier.height(8.dp))

                RegisterCustomTextField(
                    label = "NIM",
                    placeholder = "NIM",
                    value = uiState.nim,
                    onValueChange = { viewModel.updateField("nim", it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))

                RegisterCustomTextField(
                    label = "License Plate",
                    placeholder = "License Plate",
                    value = uiState.licensePlate,
                    onValueChange = { value: String -> viewModel.updateField("licensePlate", value) }
                )
                Spacer(modifier = Modifier.height(8.dp))

                CustomUploadField(
                    label = "SIM",
                    placeholder = "Upload a photo",
                    selectedUri = uiState.simImageUri,
                    onFileSelected = viewModel::updateSimImage
                )

                Spacer(modifier = Modifier.height(8.dp))

                RegisterCustomTextField(
                    label = "Email",
                    placeholder = "Enter your UC Email",
                    value = uiState.email,
                    onValueChange = { viewModel.updateField("email", it) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_email_24),
                            contentDescription = null
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))

                RegisterCustomTextField(
                    label = "Password",
                    placeholder = "Enter your Password",
                    value = uiState.password,
                    onValueChange = { viewModel.updateField("password", it) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_key_24),
                            contentDescription = null
                        )
                    },
                    isPassword = true
                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.signUp() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA155)),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Text(
                            text = "Sign Up",
                            color = Color.White,
                            modifier = Modifier.padding(8.dp),
                            fontSize = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Already have an account?", fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Log In",
                        fontSize = 12.sp,
                        color = Color(0xFFFF7637),
                        modifier = Modifier.clickable {
                            navController.navigate("Login")
                        }
                    )
                }
            }
        }
    }

    uiState.error?.let { error ->
        Text(
            text = error,
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun RegisterCustomTextField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            leadingIcon = leadingIcon,
            shape = RoundedCornerShape(50),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = if (isPassword) {
                KeyboardOptions(keyboardType = KeyboardType.Password)
            } else {
                keyboardOptions
            }
        )
    }
}


@Composable
fun CustomUploadField(
    label: String,
    placeholder: String,
    selectedUri: Uri?,
    onFileSelected: (Uri?) -> Unit
) {
    var selectedFileName by remember { mutableStateOf(placeholder) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                selectedFileName = uri.lastPathSegment ?: placeholder
                onFileSelected(uri)
            }
        }
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = selectedFileName,
            onValueChange = { },
            readOnly = true,
            placeholder = { Text(placeholder) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_upload_24),
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(50),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable {
                    launcher.launch("image/*")
                }
        )
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun RegisterViewPreview() {
    ALP_VP_2Theme {
        RegisterView(
            navController = rememberNavController()
        )
    }
}