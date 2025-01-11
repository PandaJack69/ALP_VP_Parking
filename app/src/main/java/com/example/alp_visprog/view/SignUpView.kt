package com.example.alp_vp.views

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.alp_vp.ui.theme.ALP_VPTheme
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.alp_vp.R
import com.example.alp_vp.models.UserResponse
import com.example.alp_vp.repositories.NetworkAuthenticationRepository
import com.example.alp_vp.services.AuthenticationAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SignUpView(navController: NavController) {
    // Define state for each input field
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var nim by remember { mutableStateOf("") }
    var licensePlate by remember { mutableStateOf("") }
    var simFilePath by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Instantiate the AuthenticationRepository (use ViewModel or DI in real app)
    val authenticationAPIService = remember { AuthenticationAPIService.create() } // Assuming you have a service to create the API instance
    val authenticationRepository = NetworkAuthenticationRepository(authenticationAPIService)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_gedung_uc),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.3f)
        )

        // Card containing the Sign-Up form
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

                // Bind state to each field
                CustomTextField(label = "First Name", placeholder = "First Name", value = firstName, onValueChange = { firstName = it })
                Spacer(modifier = Modifier.height(8.dp))

                CustomTextField(label = "Last Name", placeholder = "Last Name", value = lastName, onValueChange = { lastName = it })
                Spacer(modifier = Modifier.height(8.dp))

                CustomTextField(label = "NIM", placeholder = "NIM", value = nim, onValueChange = { nim = it })
                Spacer(modifier = Modifier.height(8.dp))

                CustomTextField(label = "License Plate", placeholder = "License Plate", value = licensePlate, onValueChange = { licensePlate = it })
                Spacer(modifier = Modifier.height(8.dp))

                CustomUploadField(
                    label = "SIM",
                    placeholder = "Upload a photo",
                    onFileSelected = { filePath ->
                        simFilePath = filePath ?: ""
                        println("Selected file: $filePath")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))

                CustomTextField(
                    label = "Email",
                    placeholder = "Enter your UC Email",
                    value = email,
                    onValueChange = { email = it },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_email_24),
                            contentDescription = null
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))

                CustomTextField(
                    label = "Password",
                    placeholder = "Enter your Password",
                    value = password,
                    onValueChange = { password = it },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_key_24),
                            contentDescription = null
                        )
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        // Call the signup function from the repository
                        authenticationRepository.signup(
                            firstName = firstName,
                            lastName = lastName,
                            nim = nim,
                            licensePlate = licensePlate,
                            simFilePath = simFilePath,
                            email = email,
                            password = password
                        ).enqueue(object : Callback<UserResponse> {
                            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                                if (response.isSuccessful) {
                                    // Handle successful signup
                                    val userResponse = response.body()
                                    // You can navigate or show a success message
                                } else {
                                    // Handle failure (e.g., show error message)
                                }
                            }

                            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                                // Handle failure (e.g., network issues)
                            }
                        })
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA155))
                ) {
                    Text(
                        text = "Sign Up",
                        color = Color.White,
                        modifier = Modifier.padding(8.dp),
                        fontSize = 16.sp
                    )
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
}

@Composable
fun CustomTextField(
    label: String,
    placeholder: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    value: String,
    onValueChange: (String) -> Unit
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
                .height(56.dp)
        )
    }
}

@Composable
fun CustomUploadField(
    label: String,
    placeholder: String,
    onFileSelected: (String?) -> Unit // Callback for handling the selected file path
) {
    var selectedFileName by remember { mutableStateOf(placeholder) }

    // Using GetContent() to pick an image
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                selectedFileName = uri.lastPathSegment ?: placeholder
                onFileSelected(uri.toString())
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
                    // Launch file picker on click
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
fun SignUpViewPreview() {
    ALP_VPTheme {
        SignUpView(
            navController = rememberNavController()
        )
    }
}