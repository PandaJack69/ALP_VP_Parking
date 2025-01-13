package com.example.alp_visprog.view

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alp_visprog.R
import com.example.alp_visprog.viewModel.AuthenticationViewModel
import com.example.alp_visprog.ui.theme.ALP_VisProgTheme
import com.example.alp_visprog.uiState.AuthenticationStatusUIState
import com.example.alp_visprog.view.template.AuthenticationButton
import com.example.alp_visprog.view.template.AuthenticationOutlinedNumberField
import com.example.alp_visprog.view.template.AuthenticationOutlinedTextField
import com.example.alp_visprog.view.template.PasswordOutlinedTextField

@Composable
fun RegisterView(
    authenticationViewModel: AuthenticationViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    context: Context
) {
    val registerUIState by authenticationViewModel.authenticationUIState.collectAsState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(authenticationViewModel.dataStatus) {
        val dataStatus = authenticationViewModel.dataStatus
        if (dataStatus is AuthenticationStatusUIState.Failed) {
            Toast.makeText(context, dataStatus.errorMessage, Toast.LENGTH_SHORT).show()
            authenticationViewModel.clearErrorMessage()
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

                // first name
                AuthenticationOutlinedTextField(
                    inputValue = authenticationViewModel.firstNameInput,
                    onInputValueChange = {
                        authenticationViewModel.changeFirstNameInput(it)
                        authenticationViewModel.checkRegisterForm()
                    },
                    labelText = "first name",
                    placeholderText = "First Name",
                    leadingIconSrc = painterResource(id = R.drawable.blue_car),
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardType = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    onKeyboardNext = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                // last name
                AuthenticationOutlinedTextField(
                    inputValue = authenticationViewModel.lastNameInput,
                    onInputValueChange = {
                        authenticationViewModel.changeLastNameInput(it)
                        authenticationViewModel.checkRegisterForm()
                    },
                    labelText = "last name",
                    placeholderText = "Last Name",
                    leadingIconSrc = painterResource(id = R.drawable.blue_car),
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardType = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    onKeyboardNext = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                // NIM
                AuthenticationOutlinedNumberField(
                    inputValue = authenticationViewModel.nimInput.toString(),
                    onInputValueChange = {
                        authenticationViewModel.changeNimInput(it.toInt())
                        authenticationViewModel.checkRegisterForm()
                    },
                    labelText = "NIM",
                    placeholderText = "NIM",
                    leadingIconSrc = painterResource(id = R.drawable.blue_car),
                    modifier = Modifier
                        .fillMaxWidth(),
                    onKeyboardNext = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                // license plate
                AuthenticationOutlinedTextField(
                    inputValue = authenticationViewModel.licensePlateInput,
                    onInputValueChange = {
                        authenticationViewModel.changeLicensePlateInput(it)
                        authenticationViewModel.checkRegisterForm()
                    },
                    labelText = "License Plate",
                    placeholderText = "License Plate",
                    leadingIconSrc = painterResource(id = R.drawable.blue_car),
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardType = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    onKeyboardNext = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                // SIM
                AuthenticationOutlinedNumberField(
                    inputValue = authenticationViewModel.simInput.toString(),
                    onInputValueChange = {
                        authenticationViewModel.changeSimInput(it.toInt())
                        authenticationViewModel.checkRegisterForm()
                    },
                    labelText = "SIM",
                    placeholderText = "SIM",
                    leadingIconSrc = painterResource(id = R.drawable.blue_car),
                    modifier = Modifier
                        .fillMaxWidth(),
                    onKeyboardNext = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                // email
                AuthenticationOutlinedTextField(
                    inputValue = authenticationViewModel.emailInput,
                    onInputValueChange = {
                        authenticationViewModel.changeEmailInput(it)
                        authenticationViewModel.checkRegisterForm()
                    },
//                    labelText = stringResource(id = R.string.emailText),
                    labelText = "email",
//                    placeholderText = stringResource(id = R.string.emailText),
                    placeholderText = "email",
//                    leadingIconSrc = painterResource(id = R.drawable.ic_email),
                    leadingIconSrc = painterResource(id = R.drawable.baseline_email_24),
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardType = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    onKeyboardNext = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                //password
                PasswordOutlinedTextField(
                    passwordInput = authenticationViewModel.passwordInput,
                    onPasswordInputValueChange = {
                        authenticationViewModel.changePasswordInput(it)
                        authenticationViewModel.checkRegisterForm()
                    },
                    passwordVisibilityIcon = painterResource(id = registerUIState.passwordVisibilityIcon),
//                    labelText = stringResource(id = R.string.passwordText),
                    labelText = "password",
//                    placeholderText = stringResource(id = R.string.passwordText),
                    placeholderText = "password",
                    onTrailingIconClick = {
                        authenticationViewModel.changePasswordVisibility()
                    },
                    passwordVisibility = registerUIState.passwordVisibility,
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardImeAction = ImeAction.Next,
                    onKeyboardNext = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))

                //confirm password
                PasswordOutlinedTextField(
                    passwordInput = authenticationViewModel.confirmPasswordInput,
                    onPasswordInputValueChange = {
                        authenticationViewModel.changeConfirmPasswordInput(it)
                        authenticationViewModel.checkRegisterForm()
                    },
                    passwordVisibilityIcon = painterResource(id = registerUIState.confirmPasswordVisibilityIcon),
//                    labelText = stringResource(id = R.string.confirm_password_text),
                    labelText = "confirm password",
//                    placeholderText = stringResource(id = R.string.passwordText),
                    placeholderText = "confirm password",
                    onTrailingIconClick = {
                        authenticationViewModel.changeConfirmPasswordVisibility()
                    },
                    passwordVisibility = registerUIState.confirmPasswordVisibility,
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardImeAction = ImeAction.None,
                    onKeyboardNext = KeyboardActions(
                        onDone = null
                    )
                )

                AuthenticationButton(
//                    buttonText = stringResource(id = R.string.registerText),
                    buttonText = "Register",
                    onButtonClick = {
                        authenticationViewModel.registerUser(navController)
                    },
                    buttonModifier = Modifier
                        .padding(top = 30.dp),
                    textModifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 15.dp),
                    buttonEnabled = registerUIState.buttonEnabled,
                    buttonColor = authenticationViewModel.checkButtonEnabled(registerUIState.buttonEnabled),
                    userDataStatusUIState = authenticationViewModel.dataStatus,
                    loadingBarModifier = Modifier
                        .padding(top = 30.dp)
                        .size(40.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp),
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
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var text by remember { mutableStateOf("") } // State to manage the text field value
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            placeholder = if (text.isEmpty()) ({ Text(placeholder) }) else null,
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
            keyboardOptions = keyboardOptions
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
fun RegisterViewPreview() {
    ALP_VisProgTheme {
        RegisterView(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            authenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
            navController = rememberNavController(),
            context = LocalContext.current
        )
    }

//    AppTheme {
//        RegisterView(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(20.dp),
//            authenticationViewModel = viewModel(factory = AuthenticationViewModel.Factory),
//            navController = rememberNavController(),
//            context = LocalContext.current
//        )
//    }
}