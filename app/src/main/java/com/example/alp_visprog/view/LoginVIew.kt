package com.example.alp_visprog.view

import android.content.Context
import android.widget.Toast
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.alp_visprog.enum.PagesEnum
import com.example.alp_visprog.viewModel.AuthenticationViewModel
import com.example.alp_visprog.ui.theme.ALP_VisProgTheme
import com.example.alp_visprog.uiState.AuthenticationStatusUIState
import com.example.alp_visprog.view.template.AuthenticationButton
import com.example.alp_visprog.view.template.AuthenticationOutlinedTextField
import com.example.alp_visprog.view.template.PasswordOutlinedTextField

@Composable
fun LoginView(
//    onLoginSuccess: () -> Unit
    authenticationViewModel: AuthenticationViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    context: Context
) {

    val loginUIState by authenticationViewModel.authenticationUIState.collectAsState()

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

        // Card containing the Log In form
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 300.dp),
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp)
                    .padding(top = 5.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Column {
                    Text(
                        text = "Log In",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    AuthenticationOutlinedTextField(
                        inputValue = authenticationViewModel.emailInput,
                        onInputValueChange = {
                            authenticationViewModel.changeEmailInput(it)
                            authenticationViewModel.checkLoginForm()
                        },
//                        labelText = stringResource(id = R.string.emailText),
                        labelText = "email",
//                        placeholderText = stringResource(id = R.string.emailText),
                        placeholderText = "email",
//                        leadingIconSrc = painterResource(id = R.drawable.ic_email),
                        leadingIconSrc = painterResource(R.drawable.baseline_email_24),
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

                    Spacer(modifier = Modifier.padding(5.dp))

                    PasswordOutlinedTextField(
                        passwordInput = authenticationViewModel.passwordInput,
                        onPasswordInputValueChange = {
                            authenticationViewModel.changePasswordInput(it)
                            authenticationViewModel.checkLoginForm()
                        },
                        passwordVisibilityIcon = painterResource(id = loginUIState.passwordVisibilityIcon),
//                        labelText = stringResource(id = R.string.passwordText),
                        labelText = "password",
//                        placeholderText = stringResource(id = R.string.passwordText),
                        placeholderText = "password",
                        onTrailingIconClick = {
                            authenticationViewModel.changePasswordVisibility()
                        },
                        passwordVisibility = loginUIState.passwordVisibility,
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardImeAction = ImeAction.None,
                        onKeyboardNext = KeyboardActions(
                            onDone = null
                        )
                    )

//                    CustomTextField(
//                        label = "Email",
//                        placeholder = "Enter your UC Email",
//                        leadingIcon = {
//                            Icon(
//                                painter = painterResource(id = R.drawable.baseline_email_24),
//                                contentDescription = null
//                            )
//                        }
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    CustomTextField(
//                        label = "Password",
//                        placeholder = "Enter your Password",
//                        leadingIcon = {
//                            Icon(
//                                painter = painterResource(id = R.drawable.baseline_key_24),
//                                contentDescription = null
//                            )
//                        }
//                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AuthenticationButton(
//                        buttonText = stringResource(id = R.string.loginText),
                        buttonText = "Login",
                        onButtonClick = {
                            authenticationViewModel.loginUser(navController = navController)
                        },
                        buttonModifier = Modifier
                            .padding(top = 30.dp),
                        textModifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 15.dp),
                        buttonEnabled = loginUIState.buttonEnabled,
                        buttonColor = authenticationViewModel.checkButtonEnabled(loginUIState.buttonEnabled),
                        userDataStatusUIState = authenticationViewModel.dataStatus,
                        loadingBarModifier = Modifier
                            .padding(top = 30.dp)
                            .size(40.dp)
                    )
//                    Button(
//                        onClick = {
//                            // Simulate login success or implement authentication logic
////                            onLoginSuccess()
//                        },
//                        modifier = Modifier.fillMaxWidth(),
//                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA155))
//                    ) {
//                        Text(
//                            text = "Log In",
//                            color = Color.White,
//                            modifier = Modifier.padding(8.dp),
//                            fontSize = 16.sp
//                        )
//                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 40.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Don’t have an account?", fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Sign Up",
                            fontSize = 12.sp,
                            color = Color(0xFFFF7637),
                            modifier = Modifier.clickable {
//                                navController.navigate("SignUp")
                                navController.navigate(PagesEnum.Register.name)
                            }
                        )
//                        onActionTextClicked = {
//                            authenticationViewModel.resetViewModel()
//                            navController.navigate(PagesEnum.Register.name) {
//                                popUpTo(PagesEnum.Login.name) {
//                                    inclusive = true
//                                }
//                            }
//                        },
                    }
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun LoginViewPreview() {
    ALP_VisProgTheme{
        LoginView(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            authenticationViewModel = viewModel(),
            navController = rememberNavController(),
            context = LocalContext.current
        )
    }
}
