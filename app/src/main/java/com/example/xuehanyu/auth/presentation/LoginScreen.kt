package com.example.xuehanyu.auth.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xuehanyu.R
import com.example.xuehanyu.auth.presentation.viewmodel.AuthViewModel
import com.example.xuehanyu.core.component.AnimatedButton
import com.example.xuehanyu.core.theme.XueHanYuTheme
import com.example.xuehanyu.core.theme.hanyiFontFamily

@Composable
fun LoginScreenStateless(
    email: String,
    password: String,
    errorMessage: String?,
    loading: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRememberMeChange: (Boolean) -> Unit = {},
    rememberMe: Boolean = false,
    onForgotPassword: () -> Unit = {},
    onSignUp: () -> Unit = {},
    onSkipClick: () -> Unit = { /* No-op */ }
) {
    var passwordVisible by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Skip",
            fontSize = 18.sp,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.TopEnd)
                .clickable {
                    onSkipClick()
                }
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 28.dp, vertical = 32.dp)
                .widthIn(min = 300.dp, max = 400.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.app_title),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 46.sp,
                    fontFamily = hanyiFontFamily
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.welcome_back),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    val image =
                        if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            image,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = onRememberMeChange,
                        enabled = !loading
                    )
                    Text("Remember me", fontSize = 14.sp)
                }
                TextButton(onClick = onForgotPassword, enabled = !loading) {
                    Text("Forgot Password?", fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            AnimatedButton(
                onClick = onLoginClick,
                enabled = !loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Login", fontSize = 18.sp)
            }
            if (!errorMessage.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(18.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Don't have account? ", color = Color.Gray, fontSize = 14.sp)
                Text(
                    text = "Sign up",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable(enabled = !loading, onClick = onSignUp)
                )
            }
        }
    }
}

@Composable
fun LoginScreen(
    vm: AuthViewModel,
    onRememberMeChange: (Boolean) -> Unit = {},
    rememberMe: Boolean = false,
    onForgotPassword: () -> Unit = {},
    onSignUp: () -> Unit = {},
    onSkipClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val errorMessage = vm.errorMessage.value

    LaunchedEffect(errorMessage) {
        if (!errorMessage.isNullOrEmpty()) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            vm.clearError()
        }
    }

    LoginScreenStateless(
        email = vm.email.value,
        password = vm.password.value,
        errorMessage = null,
        loading = vm.loading.value,
        onEmailChange = vm::onEmailChange,
        onPasswordChange = vm::onPasswordChange,
        onLoginClick = vm::login,
        onRememberMeChange = onRememberMeChange,
        rememberMe = rememberMe,
        onForgotPassword = onForgotPassword,
        onSignUp = onSignUp,
        onSkipClick = onSkipClick
    )
}

@Preview(showBackground = true, name = "Stateless Preview")
@Composable
fun LoginScreenStatelessPreview() {
    XueHanYuTheme {
        LoginScreenStateless(
            email = "test@example.com",
            password = "password123",
            errorMessage = "Sample error message",
            loading = false,
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            onRememberMeChange = {},
            rememberMe = true,
            onForgotPassword = {},
            onSignUp = {},
            onSkipClick = {} // ✅ Added to match new parameter
        )
    }
}
