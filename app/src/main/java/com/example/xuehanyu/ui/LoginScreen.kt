import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xuehanyu.R
import com.example.xuehanyu.ui.component.AnimatedButton
import com.example.xuehanyu.ui.component.SocialIconButton
import com.example.xuehanyu.ui.AuthViewModel

@Composable
fun LoginScreenStateless(
    email: String,
    password: String,
    errorMessage: String?,
    loading: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onFacebookClick: () -> Unit,
    onTwitterClick: () -> Unit,
    onRememberMeChange: (Boolean) -> Unit = {},
    rememberMe: Boolean = false,
    onForgotPassword: () -> Unit = {},
    onSignUp: () -> Unit = {}
) {
    var passwordVisible by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 28.dp, vertical = 32.dp)
                .widthIn(min = 300.dp, max = 400.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "XueHanYu",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Welcome back",
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
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(modifier = Modifier.weight(1f))
                Text("  Or Login with  ", color = Color.Gray, fontSize = 13.sp)
                Divider(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                SocialIconButton(
                    iconRes = R.drawable.ic_facebook_logo,
                    contentDescription = "Facebook",
                    onClick = onFacebookClick,
                    enabled = !loading
                )
                Spacer(Modifier.width(20.dp))
                SocialIconButton(
                    iconRes = R.drawable.ic_google_logo,
                    contentDescription = "Google",
                    onClick = onGoogleClick,
                    enabled = !loading
                )
                Spacer(Modifier.width(20.dp))
                SocialIconButton(
                    iconRes = R.drawable.ic_x_logo,
                    contentDescription = "Twitter X",
                    onClick = onTwitterClick,
                    enabled = !loading
                )
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
    onGoogleClick: () -> Unit,
    onFacebookClick: () -> Unit,
    onTwitterClick: () -> Unit,
    onRememberMeChange: (Boolean) -> Unit = {},
    rememberMe: Boolean = false,
    onForgotPassword: () -> Unit = {},
    onSignUp: () -> Unit = {}
) {
    LoginScreenStateless(
        email = vm.email.value,
        password = vm.password.value,
        errorMessage = vm.errorMessage.value,
        loading = vm.loading.value,
        onEmailChange = vm::onEmailChange,
        onPasswordChange = vm::onPasswordChange,
        onLoginClick = vm::login,
        onGoogleClick = onGoogleClick,
        onFacebookClick = onFacebookClick,
        onTwitterClick = onTwitterClick,
        onRememberMeChange = onRememberMeChange,
        rememberMe = rememberMe,
        onForgotPassword = onForgotPassword,
        onSignUp = onSignUp
    )
}

@Preview(showBackground = true, name = "Stateless Preview")
@Composable
fun LoginScreenStatelessPreview() {
    LoginScreenStateless(
        email = "test@example.com",
        password = "password123",
        errorMessage = "Sample error message",
        loading = false,
        onEmailChange = {},
        onPasswordChange = {},
        onLoginClick = {},
        onGoogleClick = {},
        onFacebookClick = {},
        onTwitterClick = {},
        onRememberMeChange = {},
        rememberMe = true,
        onForgotPassword = {},
        onSignUp = {}
    )
} 